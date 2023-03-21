package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mpa {
    private String name;
    private Long id;

    public Mpa(long id, String name) {
        this.id = id;
        this.name = name;
    }
}