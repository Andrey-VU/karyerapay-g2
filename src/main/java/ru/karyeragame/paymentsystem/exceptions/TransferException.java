package ru.karyeragame.paymentsystem.exceptions;

public class TransferException extends RuntimeException {
    public TransferException(String message) {
        super(message);
    }
}
