package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.messages.AnnotationMessages;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User {
    private Long id;

    @Email(message = AnnotationMessages.INCORRECT_EMAIL)
    @NotBlank(message = AnnotationMessages.EMPTY_EMAIL)
    private String email;

    @NotBlank(message = AnnotationMessages.EMPTY_LOGIN)
    @Pattern(regexp = "\\S+", message = AnnotationMessages.INCORRECT_LOGIN)
    private String login;

    private String name;

    @PastOrPresent(message = AnnotationMessages.INCORRECT_BIRTH_DATE)
    private LocalDate birthday;

    @JsonIgnore
    private Set<Long> friends = new HashSet<>();

    public void addFriend(Long id) {
        friends.add(id);
    }

    public void removeFriend(Long id) {
        friends.remove(id);
    }
    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("login", login);
        values.put("name", name);
        values.put("birthday", birthday);
        return values;
    }
}