package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private static final LocalDate BIRTH_DATE_OF_CINEMA = LocalDate.of(1895, 12, 28);
    private Long id = 1L;

    private long generateId() {
        return id++;
    }





    @Override
    public Film add( Film film) throws ValidationException {
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
        validate(film.getId());
        films.replace(film.getId(), film);
        return film;
    }

    public void validate(Long id)  {
        if (!films.containsKey(id)) {
            log.info(LogMessages.OBJECT_NOT_FOUND.toString(), id);
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
    }

    @Override
    public Film findFilmById(long id) {
        validate(id);
        return films.get(id);
    }

    @Override
    public List<Film> getAllFilms() {
        log.info(LogMessages.TOTAL.toString(), films.size());
        return new ArrayList<>(films.values());
    }
    @Override
    public void deleteFilm(long id) {
        validate(id);
        Film film = films.get(id);
        films.remove(film.getId());
    }
}
