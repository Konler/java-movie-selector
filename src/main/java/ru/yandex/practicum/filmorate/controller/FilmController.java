package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;
@RestController
public class FilmController {
    private List<Film> films=new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/film")
    public Film create (@RequestBody Film film){
        films.add(film);
        log.info("Получен запрос Post");
        return film;
    }
    @PutMapping(value = "/film")
    public Film update (@RequestBody Film film){
        films.add(film);
        log.info("Получен запрос Put");
        return film;
    }
    @GetMapping("/films")
    public List<Film> findAll() {
        log.info("Получен запрос Get");
        return films;
    }

}
