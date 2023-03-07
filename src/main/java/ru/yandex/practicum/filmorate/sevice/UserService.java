package ru.yandex.practicum.filmorate.sevice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private UserStorage userStorage;

    @Autowired
    public UserService(UserStorage storage) {

        this.userStorage = storage;
    }

    public void addFriend(Long userId, Long friendId) throws UserNotFoundException {
        User user = userStorage.findUserById(userId);
        User friend = userStorage.findUserById(friendId);
        checkIfUserNull(user);
        checkIfUserNull(friend);
        user.addFriend(friendId);
        friend.addFriend(userId);
        log.info(LogMessages.FRIEND_ADDED.toString(), friend);
    }

    public void removeFriend(Long userId, Long friendId) throws UserNotFoundException {
        User user = userStorage.findUserById(userId);
        User friend = userStorage.findUserById(friendId);
        checkIfUserNull(user);
        checkIfUserNull(friend);
        user.removeFriend(friendId);
        friend.removeFriend(userId);
        log.info(LogMessages.FRIEND_REMOVED.toString(), friend);
    }

    public List<User> getFriends(Long userId) {
        User user = userStorage.findUserById(userId);
        checkIfObjectNull(user);
        log.info(LogMessages.LIST_OF_FRIENDS.toString(), userId);
        return user.getFriends().stream()
                .map(userStorage::findUserById)
                .collect(Collectors.toList());
    }

    public User findUser(long id) {

        return userStorage.findUserById(id);
    }

    public void deleteUserById(long id) {
        userStorage.deleteUser(id);
        log.info(LogMessages.USER_DELETED.toString(), id);
    }

    public User addUser(User user) throws UserNotFoundException {
        validate(user);
        userStorage.add(user);
        log.info(LogMessages.FILM_ADDED.toString(), user);
        return user;
    }

    public void validate(User user) throws UserNotFoundException {
        checkIfUserNull(user);
        if (user.getName() == null || user.getName().isBlank()) {
            log.warn(LogMessages.EMPTY_USER_NAME.toString());
            user.setName(user.getLogin());
        }
    }

    public void checkIfUserNull(User user) throws UserNotFoundException {
        if (user == null) {
            log.warn(LogMessages.NULL_OBJECT.toString());
            throw new UserNotFoundException("Пользователь не найден");
        }
    }

    public List<User> getAll() {
        return userStorage.getAllUsers();
    }

    public User updateUser(User user) throws UserNotFoundException {
        validate(user);
        userStorage.update(user);
        log.info(LogMessages.USER_UPDATED.toString(), user);
        return user;
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        User user = userStorage.findUserById(id);
        User otherUser = userStorage.findUserById(otherId);
        checkIfObjectNull(user);
        checkIfObjectNull(otherUser);
        log.info(LogMessages.LIST_OF_COMMON_FRIENDS.toString());
        return user.getFriends().stream()
                .filter(otherUser.getFriends()::contains)
                .map(userStorage::findUserById)
                .collect(Collectors.toList());
    }

    public void checkIfObjectNull(User user) {
        if (user == null) {
            log.warn(LogMessages.NULL_OBJECT.toString());
        }
    }
}
