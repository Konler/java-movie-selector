package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<User> {
    private int id = 1;
    private final Map<Integer, User> users = new HashMap<>();

    private int generateId() {
        return id++;
    }

    @GetMapping
    @Override
    public List<User> getAll() {
        log.info(LogMessages.TOTAL.toString(), users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    @Override
    public User add(@Valid @RequestBody User user) {
        validate(user);
        if (users.containsKey(user.getId())) {
            log.info(LogMessages.ALREADY_EXIST.toString(), user);
            throw new ObjectAlreadyExistException(LogMessages.ALREADY_EXIST.toString());
        }
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info(LogMessages.OBJECT_ADD.toString(), user);
        return user;
    }

    @PutMapping
    @Override
    public User update(@Valid @RequestBody User user) {

        validate(user);
        if (users.get(user.getId()) != null) {
            users.replace(user.getId(), user);
            log.info(LogMessages.OBJECT_UPDATE.toString(), user);
        } else {
            log.info(LogMessages.OBJECT_NOT_FOUND.toString(), user);
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
        return user;
    }

    public User validate(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {
            log.warn(LogMessages.EMPTY_USER_NAME.toString());
            user.setName(user.getLogin());
        }
        return user;
    }
}