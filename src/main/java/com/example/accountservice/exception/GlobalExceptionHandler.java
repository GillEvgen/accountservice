package com.example.accountservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Обработка исключений типа ApiException с динамическим статусом
    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)  // Автоматически ставит статус NOT_FOUND для всех ApiException
    public ErrorDto handleApiException(ApiException ex, WebRequest request) {
        return new ErrorDto(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                ex.getStatus().value()  // Статус берется из исключения, но также указан @ResponseStatus
        );
    }

    // Обработка остальных исключений с статусом INTERNAL_SERVER_ERROR
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleGenericException(Exception ex, WebRequest request) {
        return new ErrorDto(
                LocalDateTime.now(),
                "An unexpected error occurred",
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }
}

