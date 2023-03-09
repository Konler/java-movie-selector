package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Film add(Film film);

    Film update(Film object);

    Optional<Film> findFilmById(long id);

    List<Film> getAllFilms();

    void deleteFilm(long id);
}
