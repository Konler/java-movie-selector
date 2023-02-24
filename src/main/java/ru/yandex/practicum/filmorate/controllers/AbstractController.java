package ru.yandex.practicum.filmorate.controllers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractController <T>{
//    private int id = 1;
//    private final Map<Integer, T> allObjects = new HashMap<>();
//
//    private int generateId() {
//        return id++;
//    }

    public abstract List<T> getAll();

    public abstract T add(T object);

    public abstract T update(T object);

    public abstract T validate(T object) throws ValidationException;

}



