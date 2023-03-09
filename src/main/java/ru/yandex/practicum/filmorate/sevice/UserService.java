package ru.yandex.practicum.filmorate.sevice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Builder
@AllArgsConstructor
public class UserService {
    private UserStorage userStorage;

    public void addFriend(Long userId, Long friendId) {
        Optional<User> userOptional = userStorage.findUserById(userId);
        User user = userOptional.orElseThrow(() -> new ObjectNotFoundException("Пользователь не найден"));
        Optional<User> friendOptional = userStorage.findUserById(friendId);
        User friend = friendOptional.orElseThrow(() -> new ObjectNotFoundException("Пользователь не найден"));
        user.addFriend(friendId);
        friend.addFriend(userId);
        log.info(LogMessages.FRIEND_ADDED.toString(), friend);
    }

    public void removeFriend(Long userId, Long friendId) {
        Optional<User> userOptional = userStorage.findUserById(userId);
        User user = userOptional.orElseThrow(() -> new ObjectNotFoundException("Пользователь не найден"));
        Optional<User> friendOptional = userStorage.findUserById(friendId);
        User friend = friendOptional.orElseThrow(() -> new ObjectNotFoundException("Пользователь не найден"));
        user.removeFriend(friendId);
        friend.removeFriend(userId);
        log.info(LogMessages.FRIEND_REMOVED.toString(), friend);
    }

    public List<User> getFriends(Long userId) {
        Optional<User> userOptional = userStorage.findUserById(userId);
        User user = userOptional.orElseThrow(() -> new ObjectNotFoundException("Пользователь не найден"));
        log.info(LogMessages.LIST_OF_FRIENDS.toString(), userId);
        return user.getFriends().stream()
                .map(userStorage::findUserByHisId)
                .collect(Collectors.toList());
    }

    public User findUser(long id) {
        Optional<User> userOptional = userStorage.findUserById(id);
        User user = userOptional.orElseThrow(() -> new ObjectNotFoundException("Пользователь не найден"));
        return user;
    }

    public void deleteUserById(long id) {
        userStorage.deleteUser(id);
        log.info(LogMessages.USER_DELETED.toString(), id);
    }

    public User addUser(User user) {
        validate(user);
        userStorage.add(user);
        log.info(LogMessages.FILM_ADDED.toString(), user);
        return user;
    }

    public void validate(User user) {
        checkIfUserNull(user);
        if (user.getName() == null || user.getName().isBlank()) {
            log.warn(LogMessages.EMPTY_USER_NAME.toString());
            user.setName(user.getLogin());
        }
    }

    public void checkIfUserNull(User object) {
        if (object == null) {
            log.warn(LogMessages.NULL_OBJECT.toString());
        }
    }

    public List<User> getAll() {
        return userStorage.getAllUsers();
    }

    public User updateUser(User user) {
        validate(user);
        userStorage.update(user);
        log.info(LogMessages.USER_UPDATED.toString(), user);
        return user;
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        Optional<User> userOptional = userStorage.findUserById(id);
        User user = userOptional.orElseThrow(() -> new ObjectNotFoundException("Пользователь не найден"));
        Optional<User> otherUserOpt = userStorage.findUserById(otherId);
        User otherUser = otherUserOpt.orElseThrow(() -> new ObjectNotFoundException("Пользователь не найден"));
        log.info(LogMessages.LIST_OF_COMMON_FRIENDS.toString());
        return user.getFriends().stream()
                .filter(otherUser.getFriends()::contains)
                .map(userStorage::findUserByHisId)
                .collect(Collectors.toList());
    }
}