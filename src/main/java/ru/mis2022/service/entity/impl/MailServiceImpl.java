package ru.mis2022.service.entity.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.mis2022.service.entity.MailService;
import ru.mis2022.utils.DateFormatter;

import javax.mail.internet.InternetAddress;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import static ru.mis2022.utils.DateFormatter.DATE_TIME_FORMATTER;

@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;


    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String mailTo, String subject, String message) {
        SimpleMailMessage mailMsg = new SimpleMailMessage();
            mailMsg.setFrom(username); // "lalal@lala.com");
            mailMsg.setTo(mailTo);
            mailMsg.setSubject(subject);
            mailMsg.setText(message);
        mailMsg.setFrom(
                LocalDateTime.now().format(DATE_TIME_FORMATTER).replaceAll("[ , ., :]", "_") + " VL <" + username + ">"
        );
//            mailMsg.setFrom(new InternetAddress("VL","VL test " + LocalDateTime.now().format(DATE_TIME_FORMATTER)));

            mailSender.send(mailMsg);

    }
}
