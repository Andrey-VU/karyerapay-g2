package ru.karyeragame.paymentsystem.exceptions;

import org.springframework.mail.MailException;

public class InvalidEmail extends MailException {
    public InvalidEmail(String msg) {
        super(msg);
    }
}
