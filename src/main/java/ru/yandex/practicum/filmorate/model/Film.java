package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.messages.AnnotationMessages;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Film {

    private Long id;

    @NotBlank(message = AnnotationMessages.EMPTY_NAME)
    private String name;

    @Size(max = 200, message = AnnotationMessages.LONG_DESCRIPTION)
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @Positive(message = AnnotationMessages.INCORRECT_DURATION)
    private int duration;

    @JsonIgnore
    private final Set<Long> likes = new HashSet<>();

    public void addLike(Long userId) {
        likes.add(userId);
    }

    public void removeLike(Long userId) {
        likes.remove(userId);
    }

    public int getLikesAmount() {
        return likes.size();
    }
}