package ru.karyeragame.paymentsystem.mailSender;

import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.karyeragame.paymentsystem.exceptions.InvalidEmail;

import java.util.Set;

@Service
public class EmailService {
    @Autowired
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //Sends only plain text mail messages
    public void sendMail(String from, Set<String> to, String subject, String body) {
        isEmailValid(from, to);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to.toArray(String[]::new));
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    //Sends HTML-formatted messages
    public void sendHtmlMail(String from, Set<String> to, String subject, String body) {
        isEmailValid(from, to);

        MimeMessage message = mailSender.createMimeMessage();
        Address[] addresses = new Address[to.size()];

        try {
            int i = 0;
            for (String email : to) {
                addresses[i++] = new InternetAddress(email);
            }

            message.setFrom(new InternetAddress(from));
            message.setRecipients(MimeMessage.RecipientType.TO, addresses);
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
        } catch (MessagingException exception) {
            throw new InvalidEmail("Не могу отправить письмо");
        }

        mailSender.send(message);
    }

    //Sends HTML-formatted messages with attachments
    public void sendHtmlMailAttachment(String from, Set<String> to, String subject, String body, FileSystemResource file) {
        isEmailValid(from, to);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to.toArray(String[]::new));
            helper.setSubject(subject);
            helper.setText(body);
            helper.addAttachment(file.getFilename(), file);
        } catch (MessagingException exception) {
            throw new InvalidEmail("Не могу отправить письмо");
        }

        mailSender.send(message);
    }

    private void isEmailValid(String from, Set<String> to) {
        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(from)) {
            throw new InvalidEmail("Некорректный формат адреса");
        }

        for (String address : to) {
            if (!validator.isValid(address)) {
                throw new InvalidEmail("Некорректный формат адреса");
            }
        }
    }
}
