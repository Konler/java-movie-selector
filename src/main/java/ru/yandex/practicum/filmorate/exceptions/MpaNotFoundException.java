package ru.yandex.practicum.filmorate.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MpaNotFoundException extends IllegalArgumentException {
    public MpaNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}