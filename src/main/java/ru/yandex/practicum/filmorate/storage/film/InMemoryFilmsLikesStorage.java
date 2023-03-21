package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@ConditionalOnProperty(name = "app.storage.type", havingValue = "memory")
public class InMemoryFilmsLikesStorage implements FilmLikesStorage {
    public static final Comparator<Film> COMPARATOR = Comparator.comparing(Film::getRate).reversed();
    private FilmStorage filmStoragestorage;
    private UserStorage userStorage;

    @Autowired
    public InMemoryFilmsLikesStorage(FilmStorage filmStoragestorage, UserStorage userStorage) {
        this.filmStoragestorage = filmStoragestorage;
        this.userStorage = userStorage;
    }

    @Override
    public void addLike(long filmId, long userId) {
        Optional<Film> film = filmStoragestorage.findFilmById(filmId);
        film.get().addLike(userId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        Optional<Film> film = filmStoragestorage.findFilmById(filmId);
        userStorage.findUserById(userId);
        film.get().removeLike(userId);
    }

    @Override
    public List<Film> getPopularFilmsList(int count) {
        return filmStoragestorage.getAllFilms().stream()
                .sorted(COMPARATOR)
                .limit(count)
                .collect(Collectors.toList());
    }
}
