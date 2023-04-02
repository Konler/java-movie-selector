package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach


    @Test
    @Order(12)
    public void addFilmWithEmptyName() throws Exception {
        Film film = Film.builder()
                .description("Film description")
                .releaseDate(LocalDate.of(2015, 6, 30))
                .duration(760)
                .rate(8)
                .mpa(new Mpa(2, "PG"))
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addFilmWithIncorrectDuration() throws Exception {
        Film film = Film.builder()
                .name("Titanic")
                .description("Film description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(-60)
                .rate(8)
                .mpa(new Mpa(2, "PG"))
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addFilmWithIncorrectDescription() throws Exception {
        Film film = Film.builder()
                .name("Titanic")
                .description("Film descriptionjfghsoufghosrugfhierugferigfuaigueiguierugeiaugheirugeirgeiurghierughierguheirugheirughirgheriugheirugheriugheriughrieugheriughriugherigherghiruehgierhgiuerhgiuerhgiurhgirugheriughrieugheirughiurghuirehgieurg")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
                .rate(8)
                .mpa(new Mpa(2, "PG"))
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addFilmWithIncorrectReleaseData() throws Exception {
        Film film = Film.builder()
                .name("Titanic")
                .description("Film description")
                .releaseDate(LocalDate.of(1894, 6, 30))
                .duration(60)
                .rate(8)
                .mpa(new Mpa(2, "PG"))
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    public void addFilmWithNullReleaseData() throws Exception {
        Film film = Film.builder()
                .name("Titanic")
                .description("Film description")
                .duration(60)
                .rate(8)
                .mpa(new Mpa(2, "PG"))
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addFilmWithCorrectData() throws Exception {
        Film film = Film.builder()
                .name("Titanic")
                .description("Film description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
                .rate(8)
                .mpa(new Mpa(3, "PG"))
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(get("/films")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addFilmWithNullMpa() throws Exception {
        Film film = Film.builder()
                .name("Titanic")
                .description("Film description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
                .rate(8)
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addAndUpdateFilmWithCorrectData() throws Exception {
        Film film = Film.builder()
                .name("Titanic")
                .description("Film description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
                .rate(8)
                .mpa(new Mpa(2, "PG"))
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        Film film2 = Film.builder()
                .id(1L)
                .name("HomeAlone")
                .description("HomeAlone description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
                .rate(8)
                .mpa(new Mpa(2, "PG"))
                .build();
        mockMvc.perform(put("/films").content(objectMapper.writeValueAsString(film2))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/films")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetFilms() throws Exception {
        Film film1 = Film.builder()
                .name("Titanic")
                .description("Titanic description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
                .rate(8)
                .mpa(new Mpa(2, "PG"))
                .build();
        Film film2 = Film.builder()
                .name("Avatar")
                .description("Avatar description")
                .releaseDate(LocalDate.of(2013, 12, 10))
                .duration(160)
                .rate(8)
                .mpa(new Mpa(2, "PG"))
                .build();
        Film film3 = Film.builder()
                .name("Spider Man")
                .description("Spider Man description")
                .releaseDate(LocalDate.of(2010, 11, 15))
                .duration(100)
                .rate(8)
                .mpa(new Mpa(2, "PG"))
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film1))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film2))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film3))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/films")
                                .content(objectMapper.writeValueAsString(List.of(film1, film2, film3)))
                                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void filmAddLike() throws Exception {
        Film film = Film.builder()
                .name("Titanic")
                .description("description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
                .mpa(new Mpa(1, null))
                .build();
        User user = User.builder()
                .email("name@email.com")
                .login("Name1234")
                .name("Name")
                .birthday(LocalDate.of(1994, 6, 19))
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        put("/films/1/like/1")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void filmRemoveLike() throws Exception {
        Film film1 = Film.builder()
                .name("Titanic")
                .description("Titanic description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
                .rate(0)
                .mpa(new Mpa(2, "PG"))
                .build();
        Film film2 = Film.builder()
                .name("Avatar")
                .description("Avatar description")
                .releaseDate(LocalDate.of(2013, 12, 10))
                .duration(160)
                .rate(0)
                .mpa(new Mpa(2, "PG"))
                .build();
        Film film3 = Film.builder()
                .name("Spider Man")
                .description("Spider Man description")
                .releaseDate(LocalDate.of(2010, 11, 15))
                .duration(100)
                .rate(0)
                .mpa(new Mpa(2, "PG"))
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film1))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film2))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film3))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/films")
                                .content(objectMapper.writeValueAsString(List.of(film1, film2, film3)))
                                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
        User user = User.builder()
                .email("name@email.com")
                .login("Name1234")
                .name("Name")
                .birthday(LocalDate.of(1994, 6, 19))
                .build();
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        put("/films/1/like/1")).andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(
                        delete("/films/1/like/1")).andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/films/popular")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addFilmAndDelete() throws Exception {
        Film film1 = Film.builder()
                .name("Titanic")
                .description("Titanic description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
                .rate(0)
                .mpa(new Mpa(2, "PG"))
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film1))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(get("/films")).andDo(print())
                .andExpect(status().isOk());
        film1.setId(1L);
        mockMvc.perform(
                        delete("/films/1"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/films")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void filmGenreUpdate() throws Exception {
        Film film1 = Film.builder()
                .name("Titanic")
                .description("Titanic description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
                .rate(0)
                .mpa(new Mpa(2, "PG"))
                .build();
        mockMvc.perform(
                        post("/films")
                                .content(objectMapper.writeValueAsString(film1))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        film1.setId(1L);
        film1.getGenres().add(new Genre(5, "Документальный"));
        LinkedHashSet<Genre> expectedSetGenre = new LinkedHashSet<>();
        expectedSetGenre.add(new Genre(5, "Документальный"));
        mockMvc.perform(
                        put("/films")
                                .content(objectMapper.writeValueAsString(film1))
                                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        result -> assertEquals(expectedSetGenre, objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                                Film.class).getGenres(), "Фильмы не совпадают")
                );
    }
}