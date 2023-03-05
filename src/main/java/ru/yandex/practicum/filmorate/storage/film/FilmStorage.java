package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.List;

public interface FilmStorage {


    Film add(Film object);

     Film update(Film object);


    Film findFilmById(long id);
    List<Film> getAllFilms();

    void deleteFilm(long id);
}
