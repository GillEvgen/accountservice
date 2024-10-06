package com.example.accountservice.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {

    // Конструктор для поиска по ID пользователя
    public UserNotFoundException(Long userId) {
        super(String.format("Пользователь с ID %d не найден", userId), HttpStatus.NOT_FOUND);
    }

    // Конструктор для поиска по номеру документа
    public UserNotFoundException(String documentNumber) {
        super(String.format("Пользователь с документом '%s' не найден", documentNumber), HttpStatus.NOT_FOUND);
    }
}
