package com.example.service;

import com.example.dto.UserCreateRequest;
import com.example.dto.UserEvent;
import com.example.dto.UserResponse;
import com.example.dto.UserUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {
    /**
     * Создаёт нового пользователя на основе переданного запроса.
     */
    UserEvent createUser(UserCreateRequest request);
    /**
     * Обновляет существующего пользователя на основе переданного запроса.
     */
    UserEvent updateUser(UserUpdateRequest request);
    /**
     * Удаляет пользователя по его идентификатору.
     */
    UserEvent deleteUser(Long id);
    /**
     * Получает информацию о пользователе по его идентификатору.
     */
    Optional<UserResponse> getUserById(Long id);
    /**
     * Получает список всех пользователей.
     */
    List<UserResponse> getAllUsers();
    /**
     * Проверяет, существует ли пользователь с указанным email.
     */
    boolean isEmailExists(String email);
}