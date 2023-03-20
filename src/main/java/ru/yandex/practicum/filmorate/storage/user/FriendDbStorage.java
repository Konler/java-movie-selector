package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mappers.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.storage.type", havingValue = "db")
@Qualifier("friendDbStorage")
public class FriendDbStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(Long userId, Long otherUserId) {
        String sql = "INSERT INTO friend (user_id, friend_id) \n" +
                "VALUES (?,?)";
        jdbcTemplate.update(sql, userId, otherUserId);
    }

    @Override
    public void removeFriend(Long userId, Long friendId) {
        String sqlQuery = "DELETE FROM friend \n" +
                "WHERE user_id = ? \n" +
                "AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
    }

    @Override
    public List<User> getFriends(Long userId) {
        String sqlQuery = "SELECT * \n" +
                "FROM user_data \n" +
                "WHERE user_id IN (\n" +
                "SELECT friend_id \n" +
                "FROM friend \n" +
                "WHERE user_id = ?) ";
        return jdbcTemplate.query(sqlQuery, new UserMapper(), userId);
    }

    @Override
    public List<User> getCommonFriends(Long id, Long otherId) {
        String sqlQuery = "SELECT * \n" +
                "FROM user_data AS u, friend AS f, friend AS fr \n" +
                "WHERE u.user_id = f.friend_id AND u.user_id = fr.friend_id \n" +
                "AND f.user_id = ? AND fr.user_id = ?";
        return jdbcTemplate.query(sqlQuery, new UserMapper(), id, otherId);
    }
}