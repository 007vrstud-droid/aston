package com.example.controller;

import com.example.api.NotificationApi;
import com.example.dto.SendEmail200Response;
import com.example.dto.SendEmailRequest;
import com.example.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
public class NotificationController implements NotificationApi {

    private final EmailService emailService;

    @Override
    public ResponseEntity<SendEmail200Response> sendEmail(@RequestBody SendEmailRequest request) {
        log.info("Получен запрос на отправку письма: email={}, subject={}, text={}",
                request.getEmail(), request.getSubject(), request.getText());
        emailService.sendCustomEmail(request.getEmail(), request.getSubject(), request.getText());
        SendEmail200Response response = new SendEmail200Response()
                .message("Письмо отправлено на " + request.getEmail());
        log.info("Ответ отправлен: {}", response.getMessage());

        return ResponseEntity.ok(response);
    }
}
