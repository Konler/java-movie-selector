package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final HashMap<Integer, User> users = new HashMap<>();
    private Integer userId = 1;

    @PutMapping
    public User updateUser(@RequestBody User user) throws ValidationException {
        if (valedate(user)) {
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
                log.info("Получен запрос Put.Обновлен пользователь с id ={}", user.getId());
            } else {
                throw new ValidationException("Нет такого пользователя");
            }
        } else {
            throw new ValidationException("Ошибка валидации пользователя");
        }

        return user;
    }

    @PostMapping
    public User createUser(@RequestBody User user) throws ValidationException {
        if (valedate(user)) {
            user.setId(userId++);
            users.put(user.getId(), user);
            log.info("Получен запрос Post. Добавлен пользователь с id ={}", user.getId());
        } else {
            throw new ValidationException("Ошибка валидации пользователя");
        }
        return user;
    }


    @GetMapping

    public List<User> findAllUsers() {
        log.info("Получен запрос Get");
        return new ArrayList<User>(users.values());
    }

    private boolean valedate(User user) {
        boolean validation = true;

        if (user.getEmail().isEmpty()) {
            validation = false;
            return validation;
        } else if (!(user.getEmail().contains("@"))) {
            validation = false;
            return validation;
        } else if (user.getLogin().isBlank() || user.getLogin().contains(" ") || user.getLogin() == null) {
            validation = false;
            return validation;
        } else if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
            if (user.getBirthday().isAfter(LocalDate.now())) {
                validation = false;
                return validation;
            }
            validation = true;
            return validation;
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            validation = false;
            return validation;
        }
        validation = true;
        return validation;
    }


}
