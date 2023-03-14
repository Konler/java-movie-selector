package ru.yandex.practicum.filmorate.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private static final Comparator<Film> FILM_POPULARITY_COMPARATOR = Comparator.comparing(Film::getLikesAmount).reversed();
    private static final LocalDate BIRTH_DATE_OF_CINEMA = LocalDate.of(1895, 12, 28);
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    public void addLike(long filmId, long userId) {
        Optional<Film> filmOptional = filmStorage.findFilmById(filmId);
        Film film = filmOptional.orElseThrow(() -> new ObjectNotFoundException("Фильм не найден"));
        userStorage.checkIfExist(userId);
        film.addLike(userId);
        log.info(LogMessages.LIKED_FILM.toString(), film);
    }

    public void removeLike(long filmId, long userId) {
        Optional<Film> filmOptional = filmStorage.findFilmById(filmId);
        Film film = filmOptional.orElseThrow(() -> new ObjectNotFoundException("Фильм не найден"));
        userStorage.checkIfExist(userId);
        film.removeLike(userId);
        log.info(LogMessages.UNLIKED_FILM.toString(), film);
    }

    public List<Film> getPopularFilmsList(int count) {
        log.info(LogMessages.POPULAR_TOTAL.toString(), count);
        return filmStorage.getSortedByWithLimit(FILM_POPULARITY_COMPARATOR, count);
    }

    public Film findFilm(long id) {
        Optional<Film> filmOptional = filmStorage.findFilmById(id);
        return filmOptional.orElseThrow(() -> new ObjectNotFoundException("Фильм не найден"));
    }

    public void deleteFilmById(long id) {
        filmStorage.deleteFilm(id);
        log.info(LogMessages.FILM_DELETED.toString(), id);
    }

    public Film addFilm(Film film) {
        validateFilmReleaseDate(film);
        filmStorage.add(film);
        log.info(LogMessages.FILM_ADDED.toString(), film);
        return film;
    }

    public Film updateFilm(Film film) {
        validateFilmReleaseDate(film);
        filmStorage.update(film);
        log.info(LogMessages.FILM_UPDATED.toString(), film);
        return film;
    }

    public List<Film> getAll() {
        return filmStorage.getAllFilms();
    }

    private void validateFilmReleaseDate(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(BIRTH_DATE_OF_CINEMA)) {
            log.warn(LogMessages.INCORRECT_FILM_RELEASE_DATE.toString());
            throw new ValidationException(LogMessages.INCORRECT_FILM_RELEASE_DATE.toString());
        }
    }
}
