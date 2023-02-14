package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.time.Instant;
import java.time.LocalDate;

@Data

public class User {
    Integer id;
    String email;
    String login;
    String name;
    LocalDate birthday;

    public User(Integer id, String email, String login, String name, LocalDate birthday) throws ValidationException {
        this.id = id;
        if (!email.isEmpty()) {
            if (email.contains("@")) {
                this.email = email;
            } else {
                throw new ValidationException("Email не может быть пустым и должен содержать знак '@'");
            }
        }
        if (login.isEmpty() || login.contains(" ")) {
            throw  new ValidationException("Логин не может быть пустым и содержать пробелы");
        } else {
            this.login = login;
        }
        if (name.isEmpty()) {
            name = login;
        } else {
            this.name = name;
        }
        if (birthday.isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        } else {
            this.birthday = birthday;
        }

    }

}
