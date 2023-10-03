package ru.karyeragame.paymentsystem.mail;

import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.karyeragame.paymentsystem.exceptions.InvalidEmail;

@Service
public class EmailService {
    @Autowired
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //Sends only plain text mail messages
    public void sendMail(String from, String[] to, String subject, String body) {
        isEmailValid(from, to);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    //Sends HTML-formatted messages
    public void sendHtmlMail(String from, String[] to, String subject, String body) {
        isEmailValid(from, to);

        MimeMessage message = mailSender.createMimeMessage();
        Address[] addresses = new Address[to.length];

        try {
            for (int i = 0; i < to.length; ++i) {
                addresses[i] = new InternetAddress(to[i]);
            }

            message.setFrom(new InternetAddress(from));
            message.setRecipients(MimeMessage.RecipientType.TO, addresses);
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
        } catch (MessagingException exception) {
            throw new InvalidEmail("Can't send email");
        }

        mailSender.send(message);
    }

    private void isEmailValid(String from, String[] to) {
        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(from)) {
            throw new InvalidEmail("Invalid email address");
        }

        for (String address : to) {
            if (!validator.isValid(address)) {
                throw new InvalidEmail("Invalid email address");
            }
        }
    }
}
