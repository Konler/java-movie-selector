package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        validate(user.getId());
        users.replace(user.getId(), user);
        return user;
    }

    public void validate(long id) throws ValidationException {
        if (!users.containsKey(id)) {
            log.info(LogMessages.OBJECT_NOT_FOUND.toString(), id);
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
    }

    @Override
    public User findUserById(long id) {
        validate(id);
        return users.get(id);
    }

    @Override
    public List<User> getAllUsers() {
        log.info(LogMessages.TOTAL.toString(), users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteUser(long id) {
        validate(id);
        User user = users.get(id);
        users.remove(user.getId());
    }
}
