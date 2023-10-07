package ru.karyeragame.paymentsystem.mailSender;

import lombok.Data;

@Data
public class MailDto {
    String from;
    String[] to;
    String subject;
    String body;
}
