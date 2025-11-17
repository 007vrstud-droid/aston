package com.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendAccountCreatedEmail(String email) {
        sendCustomEmail(email, "аккаунт создан", "аккаунт на сайте создан.");
    }

    @Override
    public void sendAccountDeletedEmail(String email) {
        sendCustomEmail(email, "аккаунт удалён", "аккаунт удалён.");
    }

    @Override
    public void sendCustomEmail(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        log.info("Письмо отправлено на {} с темой '{}'", email, subject);
    }
}