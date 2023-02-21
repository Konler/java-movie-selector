package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends AbstractController<Film> {
    private static final LocalDate BIRTH_DATE_OF_CINEMA = LocalDate.of(1895, 12, 28);

    @GetMapping
    @Override
    public List<Film> getAll() {
        return super.getAll();
    }

    @PostMapping
    @Override
    public Film objectAdd(@Valid @RequestBody Film film) throws ValidationException {
        return super.objectAdd(film);
    }

    @PutMapping
    @Override
    public Film objectRenewal(@Valid @RequestBody Film film) throws ValidationException {
        return super.objectRenewal(film);
    }

    public Film validate(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(BIRTH_DATE_OF_CINEMA)) {
            log.warn(LogMessages.INCORRECT_FILM_RELEASE_DATE.toString());
            throw new ValidationException(LogMessages.INCORRECT_FILM_RELEASE_DATE.toString());
        }
        return film;
    }
}