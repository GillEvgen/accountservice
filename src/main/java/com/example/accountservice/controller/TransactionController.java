package com.example.accountservice.controller;

import com.example.accountservice.dto.TransactionDto;
import com.example.accountservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Получение списка транзакций для аккаунта по ID с возвратом данных в формате JSON
    @GetMapping(value = "/{id}/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<TransactionDto> getTransactionsByAccountId(@PathVariable("id") Long id, Pageable pageable) {
        return transactionService.getTransactionsByAccountId(id, pageable);
    }

    // Создание новой транзакции, принимаем и возвращаем данные в формате JSON
    @PostMapping(value = "/{id}/transactions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto create(@PathVariable("id") Long id,
                                 @Valid @RequestBody TransactionDto transactionDto) throws AccountNotFoundException {
        return transactionService.create(id, transactionDto.getAmount(), transactionDto.getCurrency());
    }
}
