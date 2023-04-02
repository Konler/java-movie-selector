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
import ru.yandex.practicum.filmorate.model.Mpa;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MpaControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getMpaById() throws Exception {
        Mpa expectMpa = new Mpa(1, "G");
        mockMvc.perform(
                        get("/mpa/1")).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        result -> assertEquals(expectMpa, objectMapper.readValue(result.getResponse().getContentAsString(),
                                Mpa.class), "Рейтинг не совпадает")
                );
    }

    @Test
    public void getUnknownMpa() throws Exception {
        mockMvc.perform(get("/mpa/134663")).andDo(print())
                .andExpect(
                        status().isNotFound());
    }

    @Test
    public void getMpaAll() throws Exception {
        mockMvc.perform(get("/mpa")).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        result -> assertEquals(5, objectMapper.readValue(result.getResponse().getContentAsString(),
                                new TypeReference<ArrayList<Mpa>>() {
                                }).size(), "Рейтинги не совпадают")
                );
    }
}