package com.example.accountservice.service;

import com.example.accountservice.dto.UserDto;
import com.example.accountservice.exception.UserNotFoundException;
import com.example.accountservice.mapper.UserMapper;
import com.example.accountservice.model.User;
import com.example.accountservice.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    // Получение пользователя по ID
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto);  // Преобразуем сущность в Dto
    }

    // Получение пользователя по документу
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByDocument(String documentNumber) {
        return userRepository.findByDocumentNumber(documentNumber)
                .map(userMapper::toDto);  // Преобразуем сущность в Dto
    }

    // Получение всех пользователей с пагинацией
    @Transactional(readOnly = true)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);  // Преобразуем сущности в Dto
    }

    // Создание нового пользователя
    @Transactional
    public UserDto create(UserDto userDto) {
        User user = userMapper.toEntity(userDto);  // Преобразуем Dto в сущность
        User savedUser = userRepository.save(user);  // Сохраняем пользователя

        return userMapper.toDto(savedUser);  // Преобразуем обратно в Dto
    }

    // Удаление пользователя
    @Transactional
    public void delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        userRepository.delete(user);
    }
}