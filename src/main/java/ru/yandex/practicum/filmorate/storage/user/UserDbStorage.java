package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.mappers.UserMapper;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
//@Qualifier("userDbStorage")
@ConditionalOnProperty(name = "app.storage.type", havingValue = "db")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User add(User object) {
        String sql = "INSERT INTO user_data (email, login, name, birthday) \n" +
                "VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement psst = connection.prepareStatement(sql, new String[]{"user_id"});
            psst.setString(1, object.getEmail());
            psst.setString(2, object.getLogin());
            psst.setString(3, object.getName());
            psst.setDate(4, Date.valueOf(object.getBirthday()));
            return psst;
        }, keyHolder);
        object.setId(keyHolder.getKey().longValue());
        return object;
    }

    @Override
    public User update(User object) {
        String sql = "UPDATE user_data SET email = ?, login = ?, name = ?, birthday = ? WHERE user_id = ?";
        int userData = jdbcTemplate.update(sql, object.getEmail(), object.getLogin(), object.getName(),
                object.getBirthday(), object.getId());
        if (userData == 0) {
            log.warn(LogMessages.OBJECT_NOT_FOUND.toString());
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
        return object;
    }

    @Override
    public Optional<User> findUserById(long id) {
        String sql = "SELECT * \n" +
                "FROM user_data\n" +
                "WHERE user_data.user_id = ?;";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserMapper(), id));
        } catch (EmptyResultDataAccessException ex) {
            log.warn(LogMessages.OBJECT_NOT_FOUND.toString(), id);
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
    }

    @Override
    public void deleteUser(long id) {
        String sql = "DELETE FROM friend \n" +
                "WHERE user_id = ?\n" +
                "AND friend_id = ? ";
        jdbcTemplate.update(sql, id, id);
    }

    @Override
    public User checkIfExist(long id) {
        String sql = "SELECT * \n" +
                "FROM user_data\n" +
                "WHERE user_data.user_id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, new UserMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            log.warn(LogMessages.OBJECT_NOT_FOUND.toString(), id);
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
    }


    @Override
    @Nullable
    public User  findUserByHisId(long id) {
        String sql = "SELECT * \n" +
                "FROM user_data\n" +
                "WHERE user_data.user_id = ?;";
        try {
            return jdbcTemplate.queryForObject(sql, new UserMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            log.warn(LogMessages.OBJECT_NOT_FOUND.toString(), id);
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user_data;";
        return jdbcTemplate.query(sql, new UserMapper());
    }
}