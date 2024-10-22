package com.example.accountservice.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);  // Используем message от RuntimeException
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
