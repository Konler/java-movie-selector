package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.util.List;

@Slf4j
public abstract class AbstractController<T> {

    public abstract List<T> getAll();

    public abstract T add(T object);

    public abstract T update(T object);

    public abstract T validate(T object) throws ValidationException;

}



