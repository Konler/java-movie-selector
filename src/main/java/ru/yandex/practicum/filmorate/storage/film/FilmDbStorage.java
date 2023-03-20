package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.mappers.FilmMapper;
import ru.yandex.practicum.filmorate.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.messages.LogMessages;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

;

@Slf4j
@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.storage.type", havingValue = "db")
//@Qualifier("filmDbstorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film add(Film object) {
        String sql = "INSERT INTO film_data (name, description, release_date, duration, rate, mpa_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"film_id"});
            stmt.setString(1, object.getName());
            stmt.setString(2, object.getDescription());
            stmt.setDate(3, Date.valueOf(object.getReleaseDate()));
            stmt.setInt(4, object.getDuration());
            stmt.setLong(5, object.getRate());
            stmt.setLong(6, object.getMpa().getId());
            return stmt;
        }, keyHolder);
        object.setId(keyHolder.getKey().longValue());
        saveGenresToFilm(object);
        return object;
    }

    @Override
    public Film update(Film object) {
        String sql = "UPDATE film_data \n" +
                "SET name = ?, description = ?, release_date = ?, \n" +
                "duration = ?, rate = ?, mpa_id = ? \n" +
                "WHERE film_id = ?";
        int filmData = jdbcTemplate.update(sql,
                object.getName(),
                object.getDescription(),
                object.getReleaseDate(),
                object.getDuration(),
                object.getRate(),
                object.getMpa().getId(),
                object.getId());
        if (filmData == 0) {
            log.warn(LogMessages.OBJECT_NOT_FOUND.toString());
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
        saveGenresToFilm(object);
        return object;
    }

    @Override
    public void deleteFilm(long id) {
        String sql = "DELETE FROM film_data WHERE film_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Film> findFilmById(long id) {
        try {
            String sql = "SELECT film_data.*, mpa.mpa_name \n" +
                    "FROM film_data\n" +
                    "INNER JOIN mpa ON film_data.mpa_id = mpa.mpa_id\n" +
                    "WHERE film_data.film_id = ?";
            Film film = jdbcTemplate.queryForObject(sql, new FilmMapper(), id);

            String sqlGetGenresForFilm = "SELECT * FROM film_genre INNER JOIN genre ON film_genre.genre_id=genre.genre_id WHERE film_id=?";

            LinkedHashSet<Genre> genre = new LinkedHashSet<>(jdbcTemplate.query(sqlGetGenresForFilm, new GenreMapper(), id));
            if (film != null) {
                film.setGenres(genre);
            } else {
                log.warn(LogMessages.OBJECT_NOT_FOUND.toString());
                throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
            }
            return Optional.ofNullable(film);
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectNotFoundException(LogMessages.OBJECT_NOT_FOUND.toString());
        }
    }

    @Override
    public List<Film> getAllFilms() {
        String getFilmSql = "SELECT film_data.*, mpa.mpa_name \n" +
                "FROM film_data, mpa\n" +
                "WHERE film_data.mpa_id = mpa.mpa_id\n" +
                "ORDER BY film_data.film_id";

        List<Film> films = jdbcTemplate.query(getFilmSql, new FilmMapper());

        String sqlGetGenresForFilm = "SELECT * FROM film_genre INNER JOIN genre ON film_genre.genre_id=genre.genre_id WHERE film_id=?";

        for (Film film : films) {
            List<Genre> genres = jdbcTemplate.query(sqlGetGenresForFilm, new GenreMapper(), film.getId());
            film.setGenres(new LinkedHashSet<>(genres));
        }

        return films;
    }

    private void saveGenresToFilm(Film film) {
        final long filmId = film.getId();
        Set<Genre> genres = film.getGenres();
        jdbcTemplate.update("DELETE FROM film_genre WHERE film_id = ?", filmId);
        if (genres == null || genres.isEmpty()) {
            return;
        }
        List<Genre> genreList = new ArrayList<>(genres);
        jdbcTemplate.batchUpdate(
                "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, filmId);
                        ps.setLong(2, genreList.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return genreList.size();
                    }
                });
    }
}