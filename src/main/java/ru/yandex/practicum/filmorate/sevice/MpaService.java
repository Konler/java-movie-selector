package ru.yandex.practicum.filmorate.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaDbStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaDbStorage mpaStorage;

    public List<Mpa> getAllMpas() {
        return mpaStorage.getAllMpas();
    }

    public Mpa getMpaById(long id) {
        return mpaStorage.getMpaById(id);
    }
}