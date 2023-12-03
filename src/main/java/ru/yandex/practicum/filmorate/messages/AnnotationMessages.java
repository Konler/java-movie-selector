package ru.yandex.practicum.filmorate.messages;

public interface AnnotationMessages {
    String EMPTY_EMAIL = "email не может быть пустым";
    String INCORRECT_EMAIL = "Некорректный email";
    String INCORRECT_LOGIN = "login не должен содержать пробелы";
    String EMPTY_LOGIN = "Логин не может быть пустым";
    String INCORRECT_BIRTH_DATE = "Некорректная дата рождения";
    String EMPTY_NAME = "Название фильма не может быть пустым";
    String LONG_DESCRIPTION = "Максимальная длина описания 200 символов";
    String INCORRECT_DURATION = "Некорректная продолжительность фильма";
}