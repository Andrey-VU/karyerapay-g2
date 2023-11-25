package ru.karyeragame.paymentsystem.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(InvalidEmail.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUnknownStateException(final InvalidEmail exception) {
        log.warn(exception.getMessage());
        return Map.of("error", "Письмо не может быть отправлено");
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(Throwable exception) {
        log.warn(exception.getMessage(), exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class,
            MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequestException(Throwable exception) {
        log.warn(exception.getMessage(), exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler({IncorrectData.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleConflictException(Throwable exception) {
        log.warn(exception.getMessage(), exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler({TransferException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Map<String, String> handleServiceUnavailableException(Throwable exception) {
        log.warn(exception.getMessage(), exception);
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Map<String, String> handleUnknownException(Throwable exception) {
        log.warn(exception.getMessage(), exception);
        return Map.of("error", exception.getMessage());
    }
}
