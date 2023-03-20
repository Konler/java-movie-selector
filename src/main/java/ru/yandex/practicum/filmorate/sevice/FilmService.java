package ru.yandex.practicum.filmorate.sevice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmLikesStorage;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class FilmService {
    private static final LocalDate BIRTH_DATE_OF_CINEMA = LocalDate.of(1895, 12, 28);
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final FilmLikesStorage filmLikesStorage;

    @Autowired
    public FilmService( FilmStorage filmStorage,UserStorage userStorage, FilmLikesStorage filmAudienceStorage) {
        log.info(filmStorage.getClass().toString());
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.filmLikesStorage = filmAudienceStorage;
    }



//    @Autowired
//    public FilmService(@Qualifier("filmDbstorage") FilmStorage filmStorage,@Qualifier("userDbStorage") UserStorage userStorage,@Qualifier("filmLikesStorage") FilmLikesStorage filmAudienceStorage) {
//        log.info(filmStorage.getClass().toString());
//        this.filmStorage = filmStorage;
//        this.userStorage = userStorage;
//        this.filmLikesStorage = filmAudienceStorage;
//    }


    private void validateFilmReleaseDate(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(BIRTH_DATE_OF_CINEMA)) {
            log.warn(LogMessages.INCORRECT_FILM_RELEASE_DATE.toString());
            throw new ValidationException(LogMessages.INCORRECT_FILM_RELEASE_DATE.toString());
        }
    }

    public void addLike(long filmId, long userId) {
        Optional<Film> filmOptional = filmStorage.findFilmById(filmId);
        Film film = filmOptional.orElseThrow(() -> new ObjectNotFoundException("Фильм не найден"));
        userStorage.checkIfExist(userId);
        filmLikesStorage.addLike(filmId, userId);
        log.info(LogMessages.LIKED_FILM.toString(), film);
    }


    public void removeLike(long filmId, long userId) {
        Optional<Film> filmOptional = filmStorage.findFilmById(filmId);
        Film film = filmOptional.orElseThrow();
        userStorage.checkIfExist(userId);
        filmLikesStorage.removeLike(filmId, userId);
        log.info(LogMessages.UNLIKED_FILM.toString(), film);
    }
    public List<Film> getPopularFilmsList(int count) {
        log.info(LogMessages.POPULAR_TOTAL.toString(), count);
        return filmLikesStorage.getPopularFilmsList(count);
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
    private Film getFilmIfExist(Long filmId) {
        Optional<Film> userOptional = filmStorage.findFilmById(filmId);
        return userOptional.orElseThrow();
    }
    public Optional<Film> findFilmById(long id) {
        return filmStorage.findFilmById(id);
    }


}