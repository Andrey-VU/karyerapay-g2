package ru.karyeragame.paymentsystem.mailSender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

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
