package com.example.accountservice.exception;

import org.springframework.http.HttpStatus;

public class TransactionNotFoundException extends ApiException {

    public TransactionNotFoundException(String accountId) {
        super("Для счета с ID транзакций не найдено  " + accountId, HttpStatus.NOT_FOUND);
    }
}