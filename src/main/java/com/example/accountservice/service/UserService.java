package com.example.accountservice.service;

import com.example.accountservice.dto.UserDto;
import com.example.accountservice.exception.UserNotFoundException;
import com.example.accountservice.mapper.UserMapper;
import com.example.accountservice.model.User;
import com.example.accountservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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

    // Получение всех пользователей с пагинацией
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);  // Преобразование в DTO с помощью маппера
    }

    // Получение пользователя по ID
    public Optional<UserDto> getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDto); // Преобразуем сущность User в DTO
    }

    // Получение пользователя по номеру документа
    public Optional<UserDto> getUserByDocument(String documentNumber) {
        return userRepository.findByDocumentNumber(documentNumber)
                .map(userMapper::toDto); // Преобразуем сущность User в DTO
    }

    // Создание нового пользователя
    @Transactional
    public UserDto createUser(UserDto userDTO) {
        User user = userMapper.toEntity(userDTO); // Преобразуем DTO в сущность User
        User savedUser = userRepository.save(user); // Сохраняем нового пользователя в базе
        return userMapper.toDto(savedUser); // Преобразуем сохраненную сущность обратно в DTO и возвращаем
    }

    // Обновление пользователя
    @Transactional
    public UserDto updateUser(Long userId, UserDto userDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        // Обновляем поля пользователя
        existingUser.setName(userDTO.getName());
        existingUser.setDocumentNumber(userDTO.getDocumentNumber());
        existingUser.setDocumentType(userDTO.getDocumentType());

        User updatedUser = userRepository.save(existingUser); // Сохраняем изменения
        return userMapper.toDto(updatedUser); // Преобразуем в DTO и возвращаем
    }

    // Удаление пользователя
    @Transactional
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        userRepository.delete(existingUser); // Удаляем пользователя
    }
}