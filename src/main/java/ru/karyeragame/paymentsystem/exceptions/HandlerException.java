package ru.karyeragame.paymentsystem.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class HandlerException {
    @ExceptionHandler({MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            ErrorPassword.class,
            MethodArgumentTypeMismatchException.class,
            UserAlreadyExistException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequestException(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler({ExceptionNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Map<String, String> handleUnknownException(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler({SaveException.class})
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public Map<String, String> handleSaveException(Throwable exception) {
        log.error(exception.getMessage(), exception);
        return Map.of("error", exception.getMessage());
    }

}
