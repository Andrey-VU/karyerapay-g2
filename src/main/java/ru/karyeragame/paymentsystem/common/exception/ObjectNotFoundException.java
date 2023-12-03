package ru.karyeragame.paymentsystem.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String object, Long id) {
        super(String.format("%s с ID %s не найден!", object, id));
    }
}
