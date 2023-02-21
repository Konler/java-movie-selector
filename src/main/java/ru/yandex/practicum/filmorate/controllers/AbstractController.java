package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import ru.yandex.practicum.filmorate.exceptions.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.Object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractController<T extends Object> {
    private int id = 1;
    private final Map<Integer, T> allObjects = new HashMap<>();

    private int generateId() {
        return id++;
    }

    public List<T> getAll() {
        log.info(LogMessages.TOTAL.toString(), allObjects.size());
        return new ArrayList<>(allObjects.values());
    }

    public T objectAdd(T object) throws ValidationException {
        validate(object);
        if (allObjects.containsKey(object.getId())) {
            log.info(LogMessages.ALREADY_EXIST.toString(), object);
            throw new ObjectAlreadyExistException(LogMessages.ALREADY_EXIST.toString());
        }
        object.setId(generateId());
        allObjects.put(object.getId(), object);
        log.info(LogMessages.OBJECT_ADD.toString(), object);
        return object;
    }

    public T objectRenewal(T object) throws ValidationException {
        validate(object);
        if (allObjects.get(object.getId()) != null) {
            allObjects.replace(object.getId(), object);
            log.info(LogMessages.OBJECT_UPDATE.toString(), object);
        } else {
            log.info(LogMessages.OBJECT_NOT_FOUND.toString(), object);
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
        return object;
    }

    public abstract T validate(T object) throws ValidationException;
}