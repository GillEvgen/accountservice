package com.example.accountservice.controller;

import com.example.accountservice.dto.TransactionDto;
import com.example.accountservice.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public Page<TransactionDto> getTransactionsByAccountId(@PathVariable Long accountId,
                                                           @PageableDefault(size = 10, page = 0)Pageable pageable) {
        return transactionService.getTransactionsByAccountId(accountId, pageable);
    }

    // Создание новой транзакции
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto createTransaction(@Valid @PathVariable Long accountId, @RequestBody TransactionDto transactionDto) throws AccountNotFoundException {
        return transactionService.createTransaction(
                transactionDto.getAccountId(),
                transactionDto.getAmount(),
                transactionDto.getCurrency()
        );
    }

    // Удаление транзакции
    @DeleteMapping("/{transactionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId);
    }
}