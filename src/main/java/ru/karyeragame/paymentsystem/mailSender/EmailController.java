package ru.karyeragame.paymentsystem.mailSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
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
    public void sendMail(@RequestParam(required = false) String filename, @RequestBody MailDto mailDto) {
        if (filename == null) {
            log.info("Sending mail from: {}", mailDto.from);
            emailService.sendHtmlMail(mailDto.from, mailDto.to, mailDto.subject, mailDto.body);
        } else {
            log.info("Sending mail with Attachment {} from: {}", filename, mailDto.from);
            FileSystemResource file = new FileSystemResource(filename);
            emailService.sendHtmlMailAttachment(mailDto.from, mailDto.to, mailDto.subject, mailDto.body, file);
        }
    }
}
