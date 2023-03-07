package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User add(User object);

    User update(User object);


    User findUserById(long id);

    List<User> getAllUsers();

    void deleteUser(long id);

}
