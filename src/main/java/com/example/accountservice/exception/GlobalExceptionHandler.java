package com.example.accountservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ErrorDto handleApiException(ApiException ex, WebRequest request) {
        return new ErrorDto(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                ex.getStatus().value()
        );
    }

    @ExceptionHandler(Exception.class)
    public ErrorDto handleGenericException(Exception ex, WebRequest request) {
        return new ErrorDto(
                LocalDateTime.now(),
                "An unexpected error occurred",
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }
}
