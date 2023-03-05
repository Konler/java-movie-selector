package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.yandex.practicum.filmorate.messages.AnnotationMessages;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode
public class Film {

    private Long id;
    @Builder
    public Film(Long id, String name, String description, LocalDate releaseDate, int duration) {
        this.id=id;
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
    @JsonIgnore
    private final Set<Long> filmAudience = new HashSet<>();

    public void addLike(Long id) {
        filmAudience.add(id);
    }

    public void removeLike(Long id) {
        filmAudience.remove(id);
    }

    public int getPopularFilmsList() {
        return filmAudience.size();
    }

}