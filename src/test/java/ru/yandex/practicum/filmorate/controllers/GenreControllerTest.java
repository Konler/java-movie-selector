package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Genre;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GenreControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getGenreById() throws Exception {
        Genre expectGenre = new Genre(1, "Комедия");
        mockMvc.perform(
                        get("/genres/1")).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        result -> assertEquals(expectGenre, objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8),
                                Genre.class), "Жанры не совпадают")
                );
    }

    @Test
    public void getUnknownGenre() throws Exception {
        mockMvc.perform(
                        get("/genres/4524523453245")).andDo(print())
                .andExpect(
                        status().isNotFound());
    }

    @Test
    public void getGenreAll() throws Exception {
        mockMvc.perform(
                        get("/genres")).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        result -> assertEquals(6, objectMapper.readValue(result.getResponse().getContentAsString(),
                                new TypeReference<ArrayList<Genre>>() {
                                }).size(), "Жанры не совпадают")
                );
    }
}