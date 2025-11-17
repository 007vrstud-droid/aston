package com.example.service;

import com.example.dto.UserCreateRequest;
import com.example.dto.UserEvent;
import com.example.dto.UserResponse;
import com.example.dto.UserUpdateRequest;
import com.example.entity.UserEntity;
import com.example.exception.NotFoundException;
import com.example.kafka.UserEventPublisher;
import com.example.mapper.UserMapper;
import com.example.repository.UserRepository;
import com.example.util.UserChecks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserChecks userChecks;
    private final UserMapper userMapper;
    private final UserEventPublisher userEventPublisher;

    @Override
    public UserEvent createUser(UserCreateRequest request) {
        log.debug("Попытка создания пользователя с email: {}", request.getEmail());

        userChecks.validateUserNotNull(request);
        userChecks.validateEmail(request.getEmail());
        userChecks.validateAge(request.getAge());
        userChecks.ensureEmailUniqueForCreate(request.getEmail());

        UserEntity user = userMapper.fromCreateRequest(request);
        userRepository.save(user);
        log.info("Пользователь успешно создан: {}", user);

        UserEvent event = new UserEvent(user.getEmail(), UserEvent.EventTypeEnum.CREATED);
        userEventPublisher.publishUserEvent(event);
        return event;
    }

    @Override
    public UserEvent updateUser(UserUpdateRequest request) {
        log.debug("Попытка обновления пользователя с id: {}", request.getId());

        userChecks.validateId(request.getId());
        userChecks.validateUserNotNull(request);
        userChecks.validateEmail(request.getEmail());
        userChecks.validateAge(request.getAge());

        UserEntity existing = userRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + request.getId() + " не найден"));

        userMapper.updateEntityFromDto(request, existing);
        userChecks.ensureEmailUniqueForUpdate(existing);

        userRepository.save(existing);
        log.info("Пользователь обновлён: {}", existing);

        UserEvent event = new UserEvent(existing.getEmail(), UserEvent.EventTypeEnum.UPDATED);
        userEventPublisher.publishUserEvent(event);
        return event;
    }

    @Override
    public Optional<UserResponse> getUserById(Long id) {
        log.debug("Поиск пользователя с id: {}", id);
        userChecks.validateId(id);
        return userRepository.findById(id).map(userMapper::toResponse);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        log.debug("Получение списка всех пользователей");
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserEvent deleteUser(Long id) {
        log.debug("Попытка удалить пользователя с id: {}", id);

        userChecks.validateId(id);

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь с ID " + id + " не найден"));

        userRepository.deleteById(id);
        log.info("Пользователь с ID {} удалён", id);

        UserEvent event = new UserEvent(user.getEmail(), UserEvent.EventTypeEnum.DELETED);
        userEventPublisher.publishUserEvent(event);
        return event;
    }

    @Override
    public boolean isEmailExists(String email) {
        if (email == null || email.isBlank()) return false;
        return userRepository.findByEmail(email).isPresent();
    }
}
