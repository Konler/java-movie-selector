package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private List<User> users=new ArrayList<>();
    @PostMapping(value = "/user")
    public User create (@RequestBody User user){
        users.add(user);
        log.info("Получен запрос Post");
        return user;
    }
    @PutMapping(value = "/user")
    public User update (@RequestBody User user){
        users.add(user);
        log.info("Получен запрос Put");
        return user;
    }
    @GetMapping("/users")

    public List<User> findAll() {
        log.info("Получен запрос Get");
        return users;
    }
}
