package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/users")
    public String usersFallback() {
        return "User service is temporarily unavailable. Please try again later.";
    }

    @GetMapping("/fallback/notifications")
    public String notificationsFallback() {
        return "Notification service is temporarily unavailable. Please try again later.";
    }
}