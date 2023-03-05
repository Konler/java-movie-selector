package ru.yandex.practicum.filmorate.messages;

public enum LogMessages {
    TOTAL("Текущее количество объектов: {}"),
    ALREADY_EXIST("Такой объект {} уже есть"),
    OBJECT_ADD("Объект {} сохранен"),
    OBJECT_UPDATE("Объект {} обновлен"),
    OBJECT_NOT_FOUND("Объект {} не найден"),
    INCORRECT_FILM_RELEASE_DATE("Некорректная дата релиза! Дата релиза не может быть раньше 28.12.1895"),
    EMPTY_USER_NAME("Имя пользователя пустое. Использован логин"),
    NULL_OBJECT("Неизвестный объект"),
    ADD_FILM_REQUEST("Запрос на добавление фильма {} "),
    RENEWAL_FILM_REQUEST("Запрос на обновление фильма {} "),
    ADD_USER_REQUEST("Запрос на добавление пользователя {} "),
    RENEWAL_USER_REQUEST("Запрос на обновление пользователя {} "),
    GET_ALL_FILMS_REQUEST("Запрос на получение списка всех фильмов"),
    GET_ALL_USERS_REQUEST("Запрос на получение списка всех пользователей"),
    GET_FILM_BY_ID_REQUEST("Запрос на получение фильма с id {} "),
    DELETE_FILM_BY_ID_REQUEST("Запрос на удаление фильма с id {} "),
    DELETE_USER_BY_ID_REQUEST("Запрос на удаление пользователя с id {} "),
    GET_FRIEND_BY_ID_REQUEST("Запрос на получение друга с id {} "),
    ADD_LIKE_REQUEST("Запрос на добавление лайка фильму {} пользователем {} "),
    LIKED_FILM("Фильму {} поставлен лайк"),
    REMOVE_LIKE_REQUEST("Запрос на удаление лайка у фильма {} пользователем {} "),
    UNLIKED_FILM("У фильма {} удален лайк"),
    GET_POPULAR_REQUEST("Запрос на получение списка самых популярных фильмов"),
    POPULAR_TOTAL("Количество популярных фильмов: {}"),
    ADD_FRIEND_REQUEST("Запрос на добавление в друзья {} пользователем {} "),
    FRIEND_ADDED("{} добавлен в друзья"),
    REMOVE_FRIEND_REQUEST("Запрос на удаление из друзей {} пользователем {} "),
    FRIEND_REMOVED("{} удален из друзей"),
    GET_FRIENDS_REQUEST("Запрос на получение списка всех друзей"),
    LIST_OF_FRIENDS("Список всех друзей: "),
    GET_COMMON_FRIENDS_REQUEST("Запрос на получение списка общих друзей у пользователей {} {} "),
    LIST_OF_COMMON_FRIENDS("Список общих друзей: "),
    FILM_DELETED("Объект удален"),
    FILM_ADDED("Фильм добавлен"),
    USER_DELETED("Объект удален"),
    USER_ADDED("Фильм добавлен"),
    FILM_UPDATED("Фильм обновлен"),
    USER_UPDATED("Пользователь обновлен");
    private final String messageText;

    LogMessages(String messageText) {
        this.messageText = messageText;
    }
}