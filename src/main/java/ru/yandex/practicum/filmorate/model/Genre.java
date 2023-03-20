package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class Genre  {
    private Long id;
    private String name;

    public Genre(long id, String name) {
        this.id=id;
        this.name = name;
    }
}