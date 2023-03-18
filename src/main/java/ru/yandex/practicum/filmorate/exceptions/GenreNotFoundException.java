package ru.yandex.practicum.filmorate.exceptions;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class GenreNotFoundException extends IllegalArgumentException {
    public GenreNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}