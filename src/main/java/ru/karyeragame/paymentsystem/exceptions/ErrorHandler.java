package ru.karyeragame.paymentsystem.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.karyeragame.paymentsystem.mailSender.EmailController;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {EmailController.class})
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(InvalidEmail.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUnknownStateException(final InvalidEmail exception) {
        log.warn(exception.getMessage());
        return Map.of("error", "Письмо не может быть отправлено");
    }
}
