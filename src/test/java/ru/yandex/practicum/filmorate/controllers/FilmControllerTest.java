package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {
    FilmController filmController;
    @BeforeEach
    void seiUp() {
         filmController = new FilmController();
    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void addFilmWithEmptyName() throws Exception {
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
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addFilmWithIncorrectDuration() throws Exception {
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
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addFilmWithIncorrectDescription() throws Exception {
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
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addFilmWithIncorrectReleaseData() {
        Film film = Film.builder()
                .name("Titanic")
                .description("Film description")
                .releaseDate(LocalDate.of(1894, 6, 30))
                .duration(60)
                .build();
        assertThrows(ValidationException.class, () -> filmController.objectAdd(film),"Некорректная дата релиза!");
    }

    @Test
    public void addFilmWithNullReleaseData() throws Exception {
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
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addFilmWithCorrectData() throws Exception {
        Film film = Film.builder()
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
    public void addAndUpdateFilmWithCorrectData() throws Exception {
        Film film = Film.builder()
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
                .id(1)
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