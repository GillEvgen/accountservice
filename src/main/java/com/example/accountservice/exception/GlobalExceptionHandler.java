package com.example.accountservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Обработка исключений типа AccountNotFoundException
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("впремя", LocalDateTime.now());
        body.put("сообщения", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Обработка исключений типа TransactionNotFoundException
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<Object> handleTransactionNotFoundException(TransactionNotFoundException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("время", LocalDateTime.now());
        body.put("сообщения", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // Обработка всех остальных необработанных исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("время", LocalDateTime.now());
        body.put("сообщения", "Не ожиданная ошибка");
        body.put("ошибка", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}