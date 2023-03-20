package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.mappers.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.storage.type", havingValue = "db")
public class FilmLikesDbStorage implements FilmLikesStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(long filmId, long userId) {
        String sql = "INSERT INTO film_audience (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, filmId, userId);
        String sqlAddLike = "UPDATE film_data SET rate = rate + 1 WHERE film_id = ?";
        jdbcTemplate.update(sqlAddLike, filmId);
    }

    @Override
    public void removeLike(long filmId, long userId) {
        String sql = "DELETE FROM film_audience WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, filmId, userId);
        String sqlRemoveLike = "UPDATE film_data SET rate = rate - 1 WHERE film_id = ?";
        jdbcTemplate.update(sqlRemoveLike, filmId);
    }

    @Override
    public List<Film> getPopularFilmsList(int count) {
        String sql = "SELECT film_data.*, mpa_name FROM film_data " +
                "INNER JOIN mpa ON film_data.mpa_id = mpa.mpa_id " +
                "WHERE film_data.mpa_id = mpa.mpa_id " +
                "ORDER BY rate DESC, FILM_DATA.FILM_ID DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sql, new FilmMapper(), count);
    }
}