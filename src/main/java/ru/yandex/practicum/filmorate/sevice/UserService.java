package ru.yandex.practicum.filmorate.sevice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.constraints.NotNull;
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
        User user = getUserIfExist(userId);
        User friend = getUserIfExist(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);
        log.info(LogMessages.FRIEND_ADDED.toString(), friend);
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = getUserIfExist(userId);
        User friend = getUserIfExist(friendId);
        user.removeFriend(friendId);
        friend.removeFriend(userId);
        log.info(LogMessages.FRIEND_REMOVED.toString(), friend);
    }

    public List<User> getFriends(Long userId) {
        User user = getUserIfExist(userId);
        log.info(LogMessages.LIST_OF_FRIENDS.toString(), userId);
        return user.getFriends().stream()
                .map(userStorage::findUserByHisId)
                .collect(Collectors.toList());
    }

    public User findUser(long id) {
        return getUserIfExist(id);
    }

    public void deleteUserById(long id)  {
        userStorage.checkIfExist(id);
        userStorage.deleteUser(id);
        log.info(LogMessages.USER_DELETED.toString(), id);
    }

    public User addUser(User user) {
        validateUserName(user);
        userStorage.add(user);
        log.info(LogMessages.FILM_ADDED.toString(), user);
        return user;
    }

    public List<User> getAll() {
        return userStorage.getAllUsers();
    }

    public User updateUser(User user) {
        validateUserName(user);
        userStorage.update(user);
        log.info(LogMessages.USER_UPDATED.toString(), user);
        return user;
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        User user = getUserIfExist(id);
        User otherUser = getUserIfExist(otherId);
        log.info(LogMessages.LIST_OF_COMMON_FRIENDS.toString());
        return user.getFriends().stream()
                .filter(otherUser.getFriends()::contains)
                .map(userStorage::findUserByHisId)
                .collect(Collectors.toList());
    }

    private User getUserIfExist(Long userId) {
        Optional<User> userOptional = userStorage.findUserById(userId);
        return userOptional.orElseThrow();
    }

    private void validateUserName(@NotNull User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.warn(LogMessages.EMPTY_USER_NAME.toString());
            user.setName(user.getLogin());
        }
    }
}