package com.example.controller;

import com.example.api.UserApi;
import com.example.dto.UserCreateRequest;
import com.example.dto.UserEvent;
import com.example.dto.UserResponse;
import com.example.dto.UserResponseLinksValue;
import com.example.dto.UserUpdateRequest;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
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

        // Добавляем HATEOAS ссылки вручную
        users.forEach(user -> user.setLinks(Map.of(
                "self", new UserResponseLinksValue().href("/users/" + user.getId()),
                "all", new UserResponseLinksValue().href("/users")
        )));

        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserResponse> getUser(Long id) {
        return userService.getUserById(id)
                .map(user -> {
                    // Добавляем HATEOAS ссылки
                    user.setLinks(Map.of(
                            "self", new UserResponseLinksValue().href("/users/" + user.getId()),
                            "all", new UserResponseLinksValue().href("/users")
                    ));
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}