package com.example.service;

public interface EmailService {
    void sendAccountCreatedEmail(String email);
    void sendAccountDeletedEmail(String email);
    void sendCustomEmail(String email, String subject, String text);
}