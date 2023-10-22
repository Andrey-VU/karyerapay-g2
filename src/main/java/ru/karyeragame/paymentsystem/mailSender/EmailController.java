package ru.karyeragame.paymentsystem.mailSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/mail")
public class EmailController {
    private final EmailService emailService;

    @PostMapping
    public void sendMail(@RequestParam(required = false) String filename, @Validated @RequestBody MailDto mailDto) {
        if (filename == null) {
            log.info("Отправляю письмо от : {}", mailDto.from);
            emailService.sendHtmlMail(mailDto.from, mailDto.to, mailDto.subject, mailDto.body);
        } else {
            log.info("Отправляю письмо с приложением {} от: {}", filename, mailDto.from);
            FileSystemResource file = new FileSystemResource(filename);
            emailService.sendHtmlMailAttachment(mailDto.from, mailDto.to, mailDto.subject, mailDto.body, file);
        }
    }
}
