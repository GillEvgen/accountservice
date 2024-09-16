package com.example.accountservice.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException(Long userId) {
        super("Пользователь с ID " + userId + " не найден", HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException(String documentNumber) {
        super("Пользователь с документом  " + documentNumber + " не найден", HttpStatus.NOT_FOUND);
    }
}