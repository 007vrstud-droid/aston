package com.example.service;

public interface EmailService {
    /**
     * Отправляет письмо на указанный email, информируя пользователя о создании аккаунта.
     */
    void sendAccountCreatedEmail(String email);
    /**
     * Отправляет письмо на указанный email, информируя пользователя об удалении аккаунта.
     */
    void sendAccountDeletedEmail(String email);
    /**
     * Отправляет письмо на указанный email, информируя пользователя.
     */
    void sendCustomEmail(String email, String subject, String text);
}