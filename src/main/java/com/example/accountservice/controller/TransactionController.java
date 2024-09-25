package com.example.accountservice.controller;

import com.example.accountservice.dto.TransactionDto;
import com.example.accountservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/accounts/{accountId}/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

//    // Получение транзакций для аккаунта
//    @GetMapping("/account/{accountId}")
//    public List<TransactionDto> getTransactionsByAccountId(@PathVariable Long accountId) {
//        return transactionService.getTransactionsByAccountId(accountId);
//    }

    // Получение всех транзакций по ID аккаунта с пагинацией
    @GetMapping
    public Page<TransactionDto> getTransactionsByAccountId(@PathVariable Long accountId, Pageable pageable) {
        return transactionService.getTransactionsByAccountId(accountId, pageable);
    }

    // Создание новой транзакции
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionDto createTransaction(@Valid @RequestBody TransactionDto transactionDTO) throws AccountNotFoundException {
        return transactionService.createTransaction(
                transactionDTO.getAccountId(),
                transactionDTO.getAmount(),
                transactionDTO.getCurrency()
        );
    }
}