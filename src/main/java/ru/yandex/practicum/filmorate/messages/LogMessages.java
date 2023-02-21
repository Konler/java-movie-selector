package ru.yandex.practicum.filmorate.messages;

public enum LogMessages {
    TOTAL("Текущее количество объектов: {}"),
    ALREADY_EXIST("Такой объект {} уже есть"),
    OBJECT_ADD("Объект {} сохранен"),
    OBJECT_UPDATE("Объект {} обновлен"),
    OBJECT_NOT_FOUND("Объект {} не найден"),
    INCORRECT_FILM_RELEASE_DATE("Некорректная дата релиза! Дата релиза не может быть раньше 28.12.1895"),
    EMPTY_USER_NAME("Имя пользователя пустое. Использован логин");

    private final String messageText;

    LogMessages(String messageText) {
        this.messageText = messageText;
    }
}