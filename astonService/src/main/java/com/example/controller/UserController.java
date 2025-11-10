package com.example.controller;

import com.example.api.UserApi;
import com.example.dto.UserCreateRequest;
import com.example.dto.UserEvent;
import com.example.dto.UserResponse;
import com.example.dto.UserUpdateRequest;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserEvent> createUser(UserCreateRequest userCreateRequest) {
        UserEvent event = userService.createUser(userCreateRequest);
        return ResponseEntity.status(201).body(event);
    }

    @Override
    public ResponseEntity<UserEvent> updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        userUpdateRequest.setId(id);
        UserEvent event = userService.updateUser(userUpdateRequest);
        return ResponseEntity.ok(event);
    }

    @Override
    public ResponseEntity<UserEvent> deleteUser(Long id) {
        UserEvent event = userService.deleteUser(id);
        return ResponseEntity.ok(event);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserResponse> getUser(Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}