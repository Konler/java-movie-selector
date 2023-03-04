package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
//bvhdbrgk
@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends AbstractController<Film> {
    private final Map<Integer, Film> films = new HashMap<>();
    private static final LocalDate BIRTH_DATE_OF_CINEMA = LocalDate.of(1895, 12, 28);
    private int id = 1;
   private int generateId() {
        return id++;
    }

    @GetMapping
    @Override
    public List<Film> getAll() {
        log.info(LogMessages.TOTAL.toString(), films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    @Override
    public Film add(@Valid @RequestBody Film film) throws ValidationException {
        validate(film);
        if (films.containsKey(film.getId())) {
            log.info(LogMessages.ALREADY_EXIST.toString(), film);
            throw new ObjectAlreadyExistException(LogMessages.ALREADY_EXIST.toString());
        }
       film.setId(generateId());
        films.put(film.getId(), film);
        log.info(LogMessages.OBJECT_ADD.toString(), film);
        return film;
    }

    @PutMapping
    @Override
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        validate(film);
        if (films.get(film.getId()) != null) {
            films.replace(film.getId(), film);
            log.info(LogMessages.OBJECT_UPDATE.toString(), film);
        } else {
            log.info(LogMessages.OBJECT_NOT_FOUND.toString(), film);
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
        return film;
    }

    public Film validate(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(BIRTH_DATE_OF_CINEMA)) {
            log.warn(LogMessages.INCORRECT_FILM_RELEASE_DATE.toString());
            throw new ValidationException(LogMessages.INCORRECT_FILM_RELEASE_DATE.toString());
        }
        //film.setId(generateId());
        return film;
    }
}