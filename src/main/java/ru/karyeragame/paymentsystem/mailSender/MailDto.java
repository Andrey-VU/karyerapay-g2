package ru.karyeragame.paymentsystem.mailSender;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class MailDto {
    @Email(message = "Поле с адресом должно соответствовать формату")
    String from;

    @NotEmpty(message = "Должны быть указаны адресаты для рассылки")
    Set<@Email String> to;

    @NotBlank(message = "Поле \"Предмет письма\" должно быть заполнено")
    String subject;

    String body;
}
