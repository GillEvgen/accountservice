package com.example.accountservice.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static ApiException userNotFound(Long userId) {
        return new ApiException("Пользователь с ID %d не найден".formatted(userId), HttpStatus.NOT_FOUND);
    }
}
