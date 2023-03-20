package ru.yandex.practicum.filmorate.sevice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private FriendStorage friendStorage;
    private UserStorage userStorage;
    @Autowired
    public UserService(UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

//    @Autowired
//    public UserService(@Qualifier("userDbStorage")UserStorage userStorage, @Qualifier("friendDbStorage")FriendStorage friendStorage) {
//        this.userStorage = userStorage;
//        this.friendStorage = friendStorage;
//    }

    private void validateUserName(@NotNull User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.warn(LogMessages.EMPTY_USER_NAME.toString());
            user.setName(user.getLogin());
        }
    }
    private User getUserIfExist(Long userId) {
        Optional<User> userOptional = userStorage.findUserById(userId);
        return userOptional.orElseThrow();
    }

    public void addFriend(Long userId, Long otherUserId) {
        User user = userStorage.findUserByHisId(userId);
        User otherUser = userStorage.findUserByHisId(otherUserId);

        User friend = getFriend(userId, otherUserId);
        if (friend != null) {

            Friend unconfirmedFriend = user.getFriends().stream()
                    .filter(f -> f.getId() == otherUserId)
                    .findFirst()
                    .orElse(null);
            Friend otherUnconfirmedFriend = user.getFriends().stream()
                    .filter(f -> f.getId()== otherUserId)
                    .findFirst()
                    .orElse(null);
            if(unconfirmedFriend == null || otherUnconfirmedFriend == null) {
                log.warn(LogMessages.NULL_OBJECT.toString());
                throw new ObjectNotFoundException("Неизвестный объект!");
            } else {
                unconfirmedFriend.setFriendshipConfirm(true);
                otherUnconfirmedFriend.setFriendshipConfirm(true);

            }
        } else {
            friendStorage.addFriend(userId, otherUserId);
            log.info(LogMessages.FRIEND_ADDED.toString(), otherUser);
        }
    }


    public void removeFriend(Long userId, Long friendId) {
        User user = getUserIfExist(userId);
        User friend = getUserIfExist(friendId);
        friendStorage.removeFriend(userId, friendId);
        log.info(LogMessages.FRIEND_REMOVED.toString(), friend);
    }

    public List<User> getFriends(Long userId) {
       User user = getUserIfExist(userId);
        log.info(LogMessages.LIST_OF_FRIENDS.toString(), userId);
        return friendStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(Long id, Long otherId) {
        User user = getUserIfExist(id);
        User otherUser = getUserIfExist(otherId);
        log.info(LogMessages.LIST_OF_COMMON_FRIENDS.toString());
        return friendStorage.getCommonFriends(id, otherId);
    }

    public User getFriend(Long id, Long friendId) {
        return getFriends(id).stream().filter(user -> user.getId() == friendId).findFirst().orElse(null);
    }
    public List<User> getAll() {
        return userStorage.getAllUsers();
    }

    public User findUser(long  id){
        userStorage.checkIfExist(id);
        return userStorage.findUserById(id).get();
    }

    public void deleteUserById(long id) {
        userStorage.deleteUser(id);
    }

    public User addUser(User user) {
        validateUserName(user);
        userStorage.add(user);
        return user;
    }

    public User updateUser(User user){
        validateUserName(user);
        userStorage.update(user);
        return user;
    }
}