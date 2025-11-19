package com.example.kafka;

import com.example.dto.UserEvent;
import com.example.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventListener {

    private final EmailService emailService;

    @KafkaListener(topics = "user-events", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactory")
    public void handleUserEvent(UserEvent event) {
        log.info("Получено событие из Kafka: {}", event);
        //TODO: Временная замена отправки email на логирование
//        switch (event.getEventType()) {
//            case CREATED -> log.info("Событие CREATED для {}", event.getEmail());
//            case DELETED -> log.info("Событие DELETED для {}", event.getEmail());
//            case UPDATED -> log.info("Событие UPDATED для {}", event.getEmail());
//            default -> log.info("Игнорируем событие: {}", event.getEventType());
//        }
        switch (event.getEventType()) {
            case CREATED -> emailService.sendAccountCreatedEmail(event.getEmail());
            case DELETED -> emailService.sendAccountDeletedEmail(event.getEmail());
            case UPDATED -> emailService.sendCustomEmail(event.getEmail(),
                    "Ваш аккаунт обновлен",
                    "Здравствуйте! Данные вашего аккаунта были обновлены."
            );
            default -> log.info("Неизвестный тип события: {}", event.getEventType());
        }
    }
}
