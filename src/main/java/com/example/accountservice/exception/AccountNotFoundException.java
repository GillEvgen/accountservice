package com.example.accountservice.exception;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends ApiException {

    public AccountNotFoundException(Long accountId) {
        super("Аккаунт с  ID " + accountId + " не найден", HttpStatus.NOT_FOUND);
    }
}