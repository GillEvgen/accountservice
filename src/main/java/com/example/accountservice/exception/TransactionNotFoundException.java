package com.example.accountservice.exception;

import org.springframework.http.HttpStatus;

public class TransactionNotFoundException extends ApiException {

    public TransactionNotFoundException(String id) {
        super("Для счета с ID транзакций не найдено  " + id, HttpStatus.NOT_FOUND);
    }
}