package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.sevice.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        log.info(LogMessages.GET_ALL_USERS_REQUEST.toString());
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        log.info(LogMessages.GET_FRIEND_BY_ID_REQUEST.toString(), id);
        return userService.findUser(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable long id) {
        log.info(LogMessages.DELETE_USER_BY_ID_REQUEST.toString(), id);
        userService.deleteUserById(id);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) throws ValidationException {
        log.info(LogMessages.ADD_USER_REQUEST.toString(), user);
        userService.addUser(user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) throws ValidationException {
        log.info(LogMessages.RENEWAL_USER_REQUEST.toString(), user);
        userService.updateUser(user);
        return user;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info(LogMessages.ADD_FRIEND_REQUEST.toString(), id, friendId);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info(LogMessages.REMOVE_FRIEND_REQUEST.toString(), id, friendId);
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable long id) {
        log.info(LogMessages.GET_FRIENDS_REQUEST.toString());
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info(LogMessages.GET_COMMON_FRIENDS_REQUEST.toString());
        return userService.getCommonFriends(id, otherId);
    }
}