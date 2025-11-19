package com.example.kafka;

import com.example.dto.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventPublisher {

    private static final String TOPIC = "user-events";
    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public void publishUserEvent(UserEvent event) {
        kafkaTemplate.send(TOPIC, event.getEmail(), event);
        log.info("Отправлено событие в Kafka: {}", event);
    }
}
