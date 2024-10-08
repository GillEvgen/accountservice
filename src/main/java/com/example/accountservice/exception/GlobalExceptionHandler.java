package com.example.accountservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Обработка всех исключений типа ApiException
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleApiException(UserNotFoundException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                ex.getStatus().value()
        );
        return new ResponseEntity<>(errorDTO, ex.getStatus());
    }

    // Обработка всех остальных исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGlobalException(Exception ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                LocalDateTime.now(),
                "An unexpected error occurred",
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}