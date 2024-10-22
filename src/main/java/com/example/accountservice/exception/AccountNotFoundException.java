package com.example.accountservice.exception;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends ApiException {

    public AccountNotFoundException(Long id) {
        super("Аккаунт с  ID " + id + " не найден", HttpStatus.NOT_FOUND);
    }
}