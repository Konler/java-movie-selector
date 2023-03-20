package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {
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
                .birthday(LocalDate.of(1994, 6, 19))
                .build();
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addUserWithEmptyEmail() throws Exception {
        User user = User.builder()
                .login("Name1234")
                .name("Name")
                .birthday(LocalDate.of(1994, 6, 19))
                .build();
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addUserWithEmptyLogin() throws Exception {
        User user = User.builder()
                .email("name@email.com")
                .name("Name")
                .birthday(LocalDate.of(1994, 6, 19))
                .build();
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addUserWithIncorrectBirthday() throws Exception {
        User user = User.builder()
                .email("name@email.com")
                .login("Name1234")
                .name("Name")
                .birthday(LocalDate.of(2100, 6, 19))
                .build();
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void addUserWithCorrectData() throws Exception {
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
        mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void addAndUpdateUserWithCorrectData() throws Exception {
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
        User user2 = User.builder()
                .id(1L)
                .email("name2@email.com")
                .login(user.getLogin())
                .name("Name2")
                .birthday(LocalDate.of(1994, 6, 20))
                .build();
        mockMvc.perform(put("/users").content(objectMapper.writeValueAsString(user2))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void userGetCommonFriendsEmpty() throws Exception {
        User user1 = User.builder()
                .email("name1@email.com")
                .login("Name11")
                .name("Name1")
                .birthday(LocalDate.of(1995, 6, 19))
                .build();
        User user2 = User.builder()
                .email("name2@email.com")
                .login("Name12")
                .name("Name2")
                .birthday(LocalDate.of(1994, 5, 20))
                .build();
        mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user2))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(
                        get("/users/1/friends/common/2")).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        result -> assertEquals(0, objectMapper.readValue(result.getResponse()
                                .getContentAsString(), new TypeReference<ArrayList<User>>() {
                        }).size())
                );
    }

    @Test
    public void userAddAndGetFriend() throws Exception {
        User user1 = User.builder()
                .email("name1@email.com")
                .login("Name11")
                .name("Name1")
                .birthday(LocalDate.of(1995, 6, 19))
                .build();
        User user2 = User.builder()
                .email("name2@email.com")
                .login("Name12")
                .name("Name2")
                .birthday(LocalDate.of(1994, 5, 20))
                .build();
        mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user2))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(put("/users/1/friends/2")).andDo(print())
                .andExpectAll(
                        status().isOk()
                );
        user2.setId(2L);
        List<User> exceptFriend = List.of(user2);
        mockMvc.perform(get("/users/1/friends")).andDo(print())
                .andExpectAll(
                        status().isOk(),
                        result -> assertEquals(exceptFriend, objectMapper.readValue(result.getResponse()
                                .getContentAsString(), new TypeReference<ArrayList<User>>() {
                        }))
                );
    }

    @Test
    public void userAddUnknownFriend() throws Exception {
        User user1 = User.builder()
                .email("name1@email.com")
                .login("Name11")
                .name("Name1")
                .birthday(LocalDate.of(1995, 6, 19))
                .build();
        mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(put("/users/1/friends/224324")).andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        result -> {
                            assertNotNull(result.getResponse().getContentAsString(),
                                    "Пустое сообщение");
                            assertFalse(result.getResponse().getContentAsString().isBlank(),
                                    "Пустое сообщение");
                        }
                );
    }

    @Test
    public void userGetCommonFriends() throws Exception {
        User user1 = User.builder()
                .email("name1@email.com")
                .login("Name11")
                .name("Name1")
                .birthday(LocalDate.of(1995, 6, 19))
                .build();
        User user2 = User.builder()
                .email("name2@email.com")
                .login("Name12")
                .name("Name2")
                .birthday(LocalDate.of(1994, 5, 20))
                .build();
        User user3 = User.builder()
                .email("name3@email.com")
                .login("Name13")
                .name("Name3")
                .birthday(LocalDate.of(1991, 4, 21))
                .build();
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user1))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user2))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        post("/users")
                                .content(objectMapper.writeValueAsString(user3))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
        mockMvc.perform(
                        put("/users/1/friends/2"))
                .andDo(print());
        mockMvc.perform(
                        put("/users/3/friends/2"))
                .andDo(print());
        user2.setId(2L);
        mockMvc.perform(
                        get("/users/3/friends/common/1"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        result -> assertEquals(List.of(user2), objectMapper.readValue(result.getResponse()
                                .getContentAsString(), new TypeReference<ArrayList<User>>() {
                        }))
                );
    }

    @Test
    public void userDeleteFriend() throws Exception {
        User user1 = User.builder()
                .email("name1@email.com")
                .login("Name11")
                .name("Name1")
                .birthday(LocalDate.of(1995, 6, 19))
                .build();
        User user2 = User.builder()
                .email("name2@email.com")
                .login("Name12")
                .name("Name2")
                .birthday(LocalDate.of(1994, 5, 20))
                .build();
        mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user1))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(user2))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print());
        mockMvc.perform(
                        put("/users/1/friends/2"))
                .andDo(print());
        mockMvc.perform(
                        delete("/users/1/friends/2"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/users/1/friends"))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        result -> assertEquals(0, objectMapper.readValue(result.getResponse()
                                .getContentAsString(), new TypeReference<ArrayList<User>>() {
                        }).size())
                );
    }

    @Test
    public void addUserAndDelete() throws Exception {
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
        mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk());
        user.setId(1L);
        mockMvc.perform(
                        delete("/users/1"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/users")).andDo(print())
                .andExpect(status().isOk());
    }
}