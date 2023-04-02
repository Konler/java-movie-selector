package ru.yandex.practicum.filmorate.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("user_id"))
                .email(rs.getString("email"))
                .name(rs.getString("name"))
                .login(rs.getString("login"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }
}