package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.controllers.AbstractController;
import ru.yandex.practicum.filmorate.messages.AnnotationMessages;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class Film {
    @Positive(message = AnnotationMessages.INCORRECT_ID)
    private int id;
    @NotBlank(message = AnnotationMessages.EMPTY_NAME)
    private String name;
    @Size(max = 200, message = AnnotationMessages.LONG_DESCRIPTION)
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Positive(message = AnnotationMessages.INCORRECT_DURATION)
    private int duration;

}