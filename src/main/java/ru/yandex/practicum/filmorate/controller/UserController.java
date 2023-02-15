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

    @PostMapping
    public User createUser(@RequestBody User user) throws ValidationException {
        if (valedate(user)) {
            user.setId(userId++);
            users.put(user.getId(), user);
            log.info("Получен запрос Post. Добавлен пользователь с id ={}",user.getId());
        } else {
            throw new ValidationException("Ошибка валидации пользователя");
        }
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws ValidationException {
        if (valedate(user)){
            if (users.containsKey(user.getId())){
                users.put(user.getId(),user);
                log.info("Получен запрос Put.Обновлен пользователь с id ={}",user.getId());
            }else {
                throw new ValidationException("Нет такого пользователя");
            }
        }else {
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
        if (user.getEmail().isBlank() || user.getEmail() == null) {
            validation = false;
            return validation;
        } else if (!(user.getEmail().contains("@"))) {
            validation = false;
            return validation;
        } else if (user.getLogin().isBlank() || user.getLogin().contains(" ") || user.getLogin() == null) {
            validation = false;
            return validation;
        } else if (user.getName().isBlank() || user.getName() == null|| user.getName().isEmpty()) {
            validation = false;
            return validation;
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            validation = false;
            return validation;
        }
        validation = true;
        return validation;
    }

    public static void main(String[] args) {
        User user=new User(5,"yulya@mail.ru","Konler","",LocalDate.of(1995,11,20));
        System.out.println(user.getName());
    }
}
