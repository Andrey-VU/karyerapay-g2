package ru.karyeragame.paymentsystem.mail;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import ru.karyeragame.paymentsystem.exceptions.InvalidEmail;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailServiceTest {
    JavaMailSender mailSender = Mockito.mock(JavaMailSender.class);
    private final EmailService emailService = new EmailService(mailSender);

    @Test
    void sendMail() {
        assertThrows(InvalidEmail.class, () -> emailService.sendMail("god@universe.com",
                new String[] {"example.example.com"}, "Test", "Test body"));

        assertThrows(InvalidEmail.class, () -> emailService.sendHtmlMail("god@universe.com",
                new String[] {"example.example.com"}, "Test",
                "<h1>This is a test Spring Boot email</h1><p>It can contain <strong>HTML</strong> content.</p>"));
    }
}