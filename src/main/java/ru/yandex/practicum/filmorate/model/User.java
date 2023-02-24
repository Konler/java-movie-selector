package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.messages.AnnotationMessages;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class User extends Object {

    @Positive(message = AnnotationMessages.INCORRECT_ID)
    private int id;
    @Email(message = AnnotationMessages.INCORRECT_EMAIL)
    @NotBlank(message = AnnotationMessages.EMPTY_EMAIL)
    private String email;
    @NotBlank(message = AnnotationMessages.EMPTY_LOGIN)
    @Pattern(regexp = "\\S+", message = AnnotationMessages.INCORRECT_LOGIN)
    private String login;
    private String name;
    @PastOrPresent(message = AnnotationMessages.INCORRECT_BIRTH_DATE)
    private LocalDate birthday;

}