package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    UserController userController = new UserController();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addUserWithIncorrectEmail() throws Exception {
        User user = User.builder()
                .email("incorrect?.email.@")
                .login("Name1234")
                .name("Name")
                .birthday(LocalDate.of(1994,6,19))
                .build();
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addUserWithEmptyEmail () throws Exception {
        User user = User.builder()
                .login("Name1234")
                .name("Name")
                .birthday(LocalDate.of(1994,6,19))
                .build();
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addUserWithEmptyLogin() throws Exception {
        User user = User.builder()
                .email("name@email.com")
                .name("Name")
                .birthday(LocalDate.of(1994,6,19))
                .build();
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addUserWithIncorrectBirthday() throws Exception {
        User user = User.builder()
                .email("name@email.com")
                .login("Name1234")
                .name("Name")
                .birthday(LocalDate.of(2100,6,19))
                .build();
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addUserWithCorrectData() throws Exception {
        User user = User.builder()
                .email("name@email.com")
                .login("Name1234")
                .name("Name")
                .birthday(LocalDate.of(1994,6,19))
                .build();
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addAndUpdateUserWithCorrectData() throws Exception {
        User user = User.builder()
                .email("name@email.com")
                .login("Name1234")
                .name("Name")
                .birthday(LocalDate.of(1994,6,19))
                .build();
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        User user2 = User.builder()
                .id(1)
                .email("name2@email.com")
                .login(user.getLogin())
                .name("Name2")
                .birthday(LocalDate.of(1994,6,20))
                .build();
        mockMvc.perform(put("/users").content(objectMapper.writeValueAsString(user2))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk());
    }
}