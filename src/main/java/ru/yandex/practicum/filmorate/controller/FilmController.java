package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer filmId = 1;
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        if (validate(film)) {
            film.setId(filmId++);
            films.put(film.getId(),film);
            log.info("Получен запрос Post.Добавлен фильм c id ={}",film.getId());
        }else {
            throw new ValidationException("Ошибка валидации фильма");
        }
            return film;
        }

        @PutMapping
        public Film updateFilm (@RequestBody Film film) throws ValidationException {
            if (validate(film)) {
                if (films.containsKey(film.getId())){
                    films.put(film.getId(),film);
                    log.info("Получен запрос Put.Обновлен фильм c id ={}",film.getId());
                }else {
                    throw new ValidationException("Нет такого фильма");
                }
            }else {
                throw new ValidationException("Ошибка валидации фильма");
            }
            return film;
        }

        @GetMapping()
        public List<Film> getdAllFilms() {
            log.info("Получен запрос Get");
            return new ArrayList<Film>(films.values());
        }
        private boolean validate(Film film)  {
        boolean validation=true;
            if (film.getName().isBlank()) {
                return false;
            }else if (film.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
                validation=false;
                return validation;
            }else if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                validation = false;
                return validation;
            }else if (film.getDuration()<0) {
                validation = false;
                return validation;
            }
            validation=true;
            return validation;
        }

    }
