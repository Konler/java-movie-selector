package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private Long id = 1L;
    private final Map<Long, User> users = new HashMap<>();

    private Long generateId() {
        return id++;
    }

    @Override
    public User add(User user) {

        if (users.containsKey(user.getId())) {
            log.info(LogMessages.ALREADY_EXIST.toString(), user);
            throw new ObjectAlreadyExistException(LogMessages.ALREADY_EXIST.toString());
        }
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info(LogMessages.OBJECT_ADD.toString(), user);
        return user;
    }

    @Override
    public User update(User user) {
        checkIfExist(user.getId());
        users.replace(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findUserById(long id) {
        checkIfExist(id);
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User findUserByHisId(long id) {
        checkIfExist(id);
        return users.get(id);
    }

    @Override
    public List<User> getAllUsers() {
        log.info(LogMessages.TOTAL.toString(), users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteUser(long id) {
        checkIfExist(id);
        User user = users.get(id);
        users.remove(user.getId());
    }

    @Override
    public void checkIfExist(long id) throws ValidationException {
        if (!users.containsKey(id)) {
            log.info(LogMessages.OBJECT_NOT_FOUND.toString(), id);
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
    }
}
