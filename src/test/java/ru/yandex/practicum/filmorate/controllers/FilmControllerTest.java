package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {
    FilmController filmController;


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void addFilmWithEmptyName() throws Exception {
        Film film = Film.builder()
                .description("Film description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
                .build();
        mockMvc.perform(
                post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isInternalServerError());
    }

    @Test
    void addFilmWithIncorrectDuration() throws Exception {
        Film film = Film.builder()
                .name("Titanic")
                .description("Film description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(-60)
                .build();
        mockMvc.perform(
                post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isInternalServerError());
    }

    @Test
    void addFilmWithIncorrectDescription() throws Exception {
        Film film = Film.builder()
                .name("Titanic")
                .description("Film descriptionjfghsoufghosrugfhierugferigfuaigueiguierugeiaugheirugeirgeiurghierughierguheirugheirughirgheriugheirugheriugheriughrieugheriughriugherigherghiruehgierhgiuerhgiuerhgiurhgirugheriughrieugheirughiurghuirehgieurg")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
                .build();
        mockMvc.perform(
                post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isInternalServerError());
    }

    @Test
    void addFilmWithIncorrectReleaseData() throws Exception {
        Film film = Film.builder()
                .name("Titanic")
                .description("Film description")
                .releaseDate(LocalDate.of(1894, 6, 30))
                .duration(60)
                .build();
        mockMvc.perform(
                post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    void addFilmWithNullReleaseData() throws Exception {
        Film film = Film.builder()
                .name("Titanic")
                .description("Film description")
                .duration(60)
                .build();
        mockMvc.perform(
                post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isInternalServerError());
    }

    @Test
    void addFilmWithCorrectData() throws Exception {
        Film film = Film.builder()
                .id(100L)
                .name("Titanic")
                .description("Film description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
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
    void addAndUpdateFilmWithCorrectData() throws Exception {
        Film film = Film.builder()
                .id(1L)
                .name("Titanic")
                .description("Film description")
                .releaseDate(LocalDate.of(2012, 6, 30))
                .duration(60)
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
                .build();
        mockMvc.perform(put("/films").content(objectMapper.writeValueAsString(film2))
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/films")).andDo(print())
                .andExpect(status().isOk());
    }
}