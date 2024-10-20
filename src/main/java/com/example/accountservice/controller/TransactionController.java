package com.example.accountservice.controller;

import com.example.accountservice.dto.TransactionDto;
import com.example.accountservice.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{accountId}/transactions")
    public Page<TransactionDto> getTransactionsByAccountId(@PathVariable Long accountId, Pageable pageable) {
        return transactionService.getTransactionsByAccountId(accountId, pageable);
    }

    // Создание новой транзакции
    @PostMapping("/api/accounts/{accountId}/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto create(@PathVariable Long accountId, @Valid @RequestBody TransactionDto transactionDto) throws AccountNotFoundException {
        return transactionService.create(accountId, transactionDto.getAmount(), transactionDto.getCurrency());
    }

    // Удаление транзакции
    @DeleteMapping("/api/accounts/{accountId}/transactions/{transactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long transactionId) {
        transactionService.delete(transactionId);
    }
}