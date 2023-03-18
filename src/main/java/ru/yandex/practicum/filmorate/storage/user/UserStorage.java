package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User add(User object);

    User update(User object) ;

    Optional<User> findUserById(long id) ;

    User findUserByHisId(long id) ;

    List<User> getAllUsers();

    void deleteUser(long id) ;

    void checkIfExist(long id);
}
