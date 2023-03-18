package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
   static final Map<Long, Film> films = new HashMap<>();
    private Long id = 1L;

    private long generateId() {
        return id++;
    }

    @Override
    public Film add(Film film) throws ValidationException {
        if (films.containsKey(film.getId())) {
            log.info(LogMessages.ALREADY_EXIST.toString(), film);
            throw new ObjectAlreadyExistException(LogMessages.ALREADY_EXIST.toString());
        }
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) throws ValidationException {
        checkIfExist(film.getId());
        films.replace(film.getId(), film);
        return film;
    }

    @Override
    public void checkIfExist(Long id) {
        if (!films.containsKey(id)) {
            log.info(LogMessages.OBJECT_NOT_FOUND.toString(), id);
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
    }

    @Override
    public Optional<Film> findFilmById(long id) {
        checkIfExist(id);
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public List<Film> getAllFilms() {
        log.info(LogMessages.TOTAL.toString(), films.size());
        return new ArrayList<>(films.values());
    }

    @Override
    public void deleteFilm(long id) {
        checkIfExist(id);
        Film film = films.get(id);
        films.remove(film.getId());
    }

    @Override
    public List<Film> getSortedByWithLimit(Comparator<Film> comparator, int count) {
        return films.values().stream()
                .sorted(comparator)
                .limit(count)
                .collect(Collectors.toList());
    }
}
