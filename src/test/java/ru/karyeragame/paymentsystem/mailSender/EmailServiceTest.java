package ru.karyeragame.paymentsystem.mailSender;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import ru.karyeragame.paymentsystem.exceptions.InvalidEmail;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailServiceTest {
    JavaMailSender mailSender = Mockito.mock(JavaMailSender.class);
    private final EmailService emailService = new EmailService(mailSender);

    @Test
    void sendMail() {
        Set<String> to = new HashSet<>();
        to.add("example.example.com");

        assertThrows(InvalidEmail.class, () -> emailService.sendMail("god@universe.com",
                to, "Test", "Test body"));

        assertThrows(InvalidEmail.class, () -> emailService.sendHtmlMail("god@universe.com",
                to, "Test",
                "<h1>This is a test Spring Boot email</h1><p>It can contain <strong>HTML</strong> content.</p>"));
    }
}