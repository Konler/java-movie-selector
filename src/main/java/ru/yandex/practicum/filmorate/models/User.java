package ru.yandex.practicum.filmorate.models;

import lombok.*;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Data

public class User {
    Integer id;
    String email;
    String login;
    @NonNull
    String name;
    LocalDate birthday;

    public User(Integer id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    //    public User(Integer id, String email, String login, String name, LocalDate birthday) throws ValidationException {
//        this.id = id;
//        if (!email.isEmpty()) {
//            if (email.contains("@")) {
//                this.email = email;
//            } else {
//                throw new ValidationException("Email не может быть пустым и должен содержать знак '@'");
//            }
//        }
//        if (login.isEmpty() || login.contains(" ")) {
//            throw  new ValidationException("Логин не может быть пустым и содержать пробелы");
//        } else {
//            this.login = login;
//        }
//        if (name.isEmpty()) {
//            name = login;
//        } else {
//            this.name = name;
//        }
//        if (birthday.isAfter(LocalDate.now())) {
//            throw new ValidationException("Дата рождения не может быть в будущем");
//        } else {
//            this.birthday = birthday;
//        }
//
//    }

}
