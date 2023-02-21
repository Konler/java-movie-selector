package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.messages.AnnotationMessages;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class Film extends Object {
    @Builder
    public Film(int id, String name, String description, LocalDate releaseDate, int duration) {
        super(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    @NotBlank(message = AnnotationMessages.EMPTY_NAME)
    private String name;
    @Size(max = 200, message = AnnotationMessages.LONG_DESCRIPTION)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive(message = AnnotationMessages.INCORRECT_DURATION)
    private int duration;
}