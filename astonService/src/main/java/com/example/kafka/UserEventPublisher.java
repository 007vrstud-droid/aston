package com.example.kafka;

import com.example.dto.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEventPublisher {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;
    private static final String TOPIC = "user-events";

    public void publishUserEvent(UserEvent event) {
        kafkaTemplate.send(TOPIC, event.getEmail(), event);
        log.info("Отправлено событие в Kafka: {}", event);
    }
}
