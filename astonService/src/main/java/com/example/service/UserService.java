package com.example.service;

import com.example.dto.UserCreateRequest;
import com.example.dto.UserEvent;
import com.example.dto.UserResponse;
import com.example.dto.UserUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserEvent createUser(UserCreateRequest request);

    UserEvent updateUser(UserUpdateRequest request);

    UserEvent deleteUser(Long id);

    Optional<UserResponse> getUserById(Long id);

    List<UserResponse> getAllUsers();

    boolean isEmailExists(String email);
}