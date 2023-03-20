package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@ConditionalOnProperty(name = "app.storage.type", havingValue = "memory")
public class InMemoryFriendStorage implements  FriendStorage {
    private UserStorage userStorage;

    @Autowired
    public InMemoryFriendStorage(UserStorage storage) {
        this.userStorage = userStorage;
    }

    @Override
    public void addFriend(Long userId, Long otherUserId) {
        Optional<User> user = userStorage.findUserById(userId);
        Optional<User> otherUser = userStorage.findUserById(otherUserId);
        user.get().addFriend(otherUserId);
        otherUser.get().addFriend(userId);
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        Optional<User> user = userStorage.findUserById(userId);
        Optional<User> friend = userStorage.findUserById(friendId);
        user.get().removeFriend(friendId);
        friend.get().removeFriend(userId);
    }

    @Override
    public List<User> getFriends(Long userId) {
        Optional<User> user = userStorage.findUserById(userId);
        return user.get().getFriends().stream()
                .map(Friend::getId)
                .map(userStorage::findUserByHisId)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        Optional<User> user = userStorage.findUserById(id);
        Optional<User> otherUser = userStorage.findUserById(otherId);
        return user.get().getFriends().stream()
                .filter(otherUser.get().getFriends()::contains)
                .map(Friend::getId)
                .map(userStorage::findUserByHisId)
                .collect(Collectors.toList());
    }
}
