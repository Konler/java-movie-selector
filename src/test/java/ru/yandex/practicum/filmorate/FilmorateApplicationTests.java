package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {
    int id = 0;

//    @Test
//    void shouldNotThrowExceptionFilm() throws ValidationException {
//        Film film;
//        Assertions.assertDoesNotThrow(new Executable() {
//            @Override
//            public void execute() throws Throwable {
//                new Film(0, "Name", "Description", LocalDate.of(1899, 01, 01), Duration.ofMinutes(150));
//            }
//        });
//    }
//
//    @Test
//    void shouldThrowExifNameIsEmpty() throws ValidationException {
//        Film film;
//        ValidationException ex = assertThrows(ValidationException.class, new Executable() {
//            @Override
//            public void execute() throws Throwable {
//                new Film(0, "", "Description", LocalDate.of(1899, 01, 01), Duration.ofMinutes(150));
//            }
//        });
//        assertEquals("Пустое имя", ex.getMessage());
//    }
//
//    @Test
//    void shouldThrowExIfDescriptionMoreThan200() throws ValidationException {
//        Film film;
//        ValidationException ex = assertThrows(ValidationException.class, new Executable() {
//            @Override
//            public void execute() throws Throwable {
//                new Film(0, "Name", "Description is a compositional form that is used in literary studies and linguistics to describe objects or phenomena in detail in order to create an artistic image.\n" +
//                        "\n" +
//                        "This compositional form is divided into a description of objects, a description of processes, a description of the experience or a description of a person's life and characteristics.\n" +
//                        "\n" +
//                        "A complex object forms an abstract semantic type used to describe objects. For this compositional form, an image of spatial relations is typical, in which the spatial relationship of the elements of the object is also displayed. It is these elements that give the text visibility, which is realized through the use of space means — adverbs of place or spatial designations, given directions, distances and shapes, as well as the corresponding direction of adjectives. In the description, the time of observation of the object or object also coincides with the time of the description.\n" +
//                        "\n" +
//                        "The subject can be displayed in the past tense using all linguistic means", LocalDate.of(1899, 01, 01), Duration.ofMinutes(150));
//            }
//        });
//        assertEquals("Описание не может иметь более 200 символов", ex.getMessage());
//    }
//
//    @Test
//    void shouldThrowExifDataBefore1985() throws ValidationException {
//        Film film;
//        ValidationException ex = assertThrows(ValidationException.class, new Executable() {
//            @Override
//            public void execute() throws Throwable {
//                new Film(0, "Name", "Description", LocalDate.of(1859, 01, 01), Duration.ofMinutes(150));
//            }
//        });
//        assertEquals("Дата релиза не должна быть раньше 28.12.1895", ex.getMessage());
//    }
//    @Test
//    void shouldThrowExifDurationIsNegative() throws ValidationException {
//        Film film;
//        ValidationException ex = assertThrows(ValidationException.class, new Executable() {
//            @Override
//            public void execute() throws Throwable {
//                new Film(0, "Name", "Description", LocalDate.of(1899, 01, 01), Duration.ofMinutes(-150));
//            }
//        });
//        assertEquals("Продолжительность фильма не может быть отрицательной", ex.getMessage());
//    }
//    @Test
//    void shouldNotThrowExceptionUser() throws ValidationException {
//        User user;
//        Assertions.assertDoesNotThrow(new Executable() {
//            @Override
//            public void execute() throws Throwable {
//                new User(0, "user@email.com","userLogin","UserName", LocalDate.of(1995,11,20));
//            }
//        });
//    }
//    @Test
//    void shouldThrowExifEmailIsEmptyOrNotContains() throws ValidationException {
//        User user;
//        ValidationException ex = assertThrows(ValidationException.class, new Executable() {
//            @Override
//            public void execute() throws Throwable {
//                new User(0, " ","userLogin","UserName", LocalDate.of(1995,11,20));
//            }
//        });
//        assertEquals("Email не может быть пустым и должен содержать знак '@'", ex.getMessage());
//    }
//
//    @Test
//    void shouldThrowExIfLoginIsEmpty() throws ValidationException {
//        User user;
//        ValidationException ex = assertThrows(ValidationException.class, new Executable() {
//            @Override
//            public void execute() throws Throwable {
//                new User(0, "user@mail.ru ","user Login","UserName", LocalDate.of(1995,11,20));
//            }
//        });
//        assertEquals("Логин не может быть пустым и содержать пробелы", ex.getMessage());
//    }
//    @Test
//    void shouldThrowExIfBirthdayInFuture() throws ValidationException {
//        User user;
//        ValidationException ex = assertThrows(ValidationException.class, new Executable() {
//            @Override
//            public void execute() throws Throwable {
//                new User(0, "user@mail.ru ","userLogin","UserName", LocalDate.of(2995,11,20));
//            }
//        });
//        assertEquals("Дата рождения не может быть в будущем", ex.getMessage());
//    }
//
//




}
