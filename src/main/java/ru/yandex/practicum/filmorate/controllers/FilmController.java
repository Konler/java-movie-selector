package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.sevice.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    @Autowired
    public FilmController(FilmService filmService) {

        this.filmService = filmService;
    }
    @GetMapping
    public List<Film> getAllFilms() {
        log.info(LogMessages.GET_ALL_FILMS_REQUEST.toString());
        return filmService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Film> getFilmById(@PathVariable long id) {
        log.info(LogMessages.GET_FILM_BY_ID_REQUEST.toString(), id);
        return filmService.findFilmById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteFilmById(@PathVariable long id) {
        log.info(LogMessages.DELETE_FILM_BY_ID_REQUEST.toString(), id);
        filmService.deleteFilmById(id);
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        log.info(LogMessages.ADD_FILM_REQUEST.toString(), film);
        filmService.addFilm(film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException {
        log.info(LogMessages.RENEWAL_FILM_REQUEST.toString(), film);
        filmService.updateFilm(film);
        return film;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable long id, @PathVariable long userId) {
        log.info(LogMessages.ADD_LIKE_REQUEST.toString(), id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable long id, @PathVariable long userId) {
        log.info(LogMessages.REMOVE_LIKE_REQUEST.toString(), id, userId);
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info(LogMessages.GET_POPULAR_REQUEST.toString());
        return filmService.getPopularFilmsList(count);
    }
}
