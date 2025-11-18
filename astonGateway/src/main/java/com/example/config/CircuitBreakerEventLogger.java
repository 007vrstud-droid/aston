package com.example.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnFailureRateExceededEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnErrorEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnResetEvent;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnCallNotPermittedEvent;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CircuitBreakerEventLogger {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @PostConstruct
    public void registerEventListeners() {
        circuitBreakerRegistry.getAllCircuitBreakers().forEach(this::registerEvents);
    }

    private void registerEvents(CircuitBreaker circuitBreaker) {
        circuitBreaker.getEventPublisher()
                .onStateTransition(this::onStateTransition)
                .onFailureRateExceeded(this::onFailureRateExceeded)
                .onError(this::onError)
                .onCallNotPermitted(this::onCallNotPermitted)
                .onReset(this::onReset);

        log.info("Зарегистрирован логгер для CircuitBreaker: {}", circuitBreaker.getName());
    }

    private void onStateTransition(CircuitBreakerOnStateTransitionEvent event) {
        log.warn("CircuitBreaker '{}' перешёл из {} в {}",
                event.getCircuitBreakerName(),
                event.getStateTransition().getFromState(),
                event.getStateTransition().getToState());
    }

    private void onFailureRateExceeded(CircuitBreakerOnFailureRateExceededEvent event) {
        log.warn("CircuitBreaker '{}' превысил порог ошибок: {}%",
                event.getCircuitBreakerName(),
                event.getFailureRate());
    }

    private void onError(CircuitBreakerOnErrorEvent event) {
        log.error("Ошибка в '{}' — {}",
                event.getCircuitBreakerName(),
                event.getThrowable().toString());
    }

    private void onCallNotPermitted(CircuitBreakerOnCallNotPermittedEvent event) {
        log.warn("CircuitBreaker '{}' заблокировал вызов — состояние OPEN",
                event.getCircuitBreakerName());
    }

    private void onReset(CircuitBreakerOnResetEvent event) {
        log.info("CircuitBreaker '{}' сброшен в состояние CLOSED",
                event.getCircuitBreakerName());
    }
}