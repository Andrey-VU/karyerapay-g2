package ru.karyeragame.paymentsystem.exceptions;

public class ErrorPassword extends RuntimeException {
    public ErrorPassword(String message) {
        super(message);
    }
}
