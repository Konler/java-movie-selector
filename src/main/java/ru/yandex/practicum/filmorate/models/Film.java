package ru.yandex.practicum.filmorate.models;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Data
public class Film {
    Integer id;
    @NotNull
    String name;
    @NotNull
    String description;
    @NotNull
    LocalDate releaseDate;
    @NotNull
    int duration;

//    public Film(Integer id, String name, String description, LocalDate releaseDate, Duration duration) throws ValidationException {
//        this.id = id;
//        if (name.isEmpty()) {
//            throw new ValidationException("Пустое имя");
//        } else {
//            this.name = name;
//        }
//        if (description.length() > 200) {
//            throw new ValidationException("Описание не может иметь более 200 символов");
//        } else {
//            this.description = description;
//        }
//        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
//            throw new ValidationException("Дата релиза не должна быть раньше 28.12.1895");
//        } else {
//            this.releaseDate = releaseDate;
//        }
//        if (duration.isNegative()) {
//            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
//        } else {
//            this.duration = duration;
//        }
//    }


    public Film(Integer id, String name, String description, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
