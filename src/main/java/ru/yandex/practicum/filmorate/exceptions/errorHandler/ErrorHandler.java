package ru.yandex.practicum.filmorate.exceptions.errorHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice("ru.yandex.practicum.filmorate.controllers")
public class ErrorHandler {
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String, String> handleValidationException(final ValidationException e) {
//        log.info("Ошибка 400!");
//        return Map.of("error", "Ошибка валидации",
//                "errorMessage", e.getMessage());
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public Map<String, String> handleObjectNotFoundException(final ObjectNotFoundException e) {
//        log.info("Ошибка 404!");
//        return Map.of("error", "Искомый объект не найден",
//                "errorMessage", e.getMessage());
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public Map<String, String> handleThrowable(final Throwable e) {
//        log.info("Ошибка 500!");
//        return Map.of("error", e.getMessage());
//    }
}