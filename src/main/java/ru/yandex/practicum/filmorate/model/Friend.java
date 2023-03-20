package ru.yandex.practicum.filmorate.model;
import lombok.Data;

@Data
public class Friend {
    private Long id;
    private boolean isFriendshipConfirm;

    public void setId(Long id) {
        this.id = id;
    }
}