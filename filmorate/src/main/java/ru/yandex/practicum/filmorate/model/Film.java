package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Data
public class Film {
    Integer id;
    String name;
    String description;
    LocalDate releaseDate;
    Duration duration;

    public Film(Integer id, String name, String description, LocalDate releaseDate, Duration duration) throws ValidationException {
        this.id = id;
        if (name.isEmpty()) {
            throw new ValidationException("Пустое имя");
        } else {
            this.name = name;
        }
        if (description.length() > 200) {
            throw new ValidationException("Описание не может иметь более 200 символов");
        } else {
            this.description = description;
        }
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Дата релиза не должна быть раньше 28.12.1895");
        } else {
            this.releaseDate = releaseDate;
        }
        if (duration.isNegative()) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        } else {
            this.duration = duration;
        }
    }
}
