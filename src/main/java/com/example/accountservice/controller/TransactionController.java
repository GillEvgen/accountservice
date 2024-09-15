package com.example.accountservice.controller;

import com.example.accountservice.model.Transaction;
import com.example.accountservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Создание новой транзакции
    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(
            @RequestParam Long accountId,
            @RequestParam BigDecimal amount,
            @RequestParam String currency) throws AccountNotFoundException {

        Transaction newTransaction = transactionService.createTransaction(accountId, amount, currency);
        return ResponseEntity.ok(newTransaction);
    }

    // Получение списка транзакций для аккаунта
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }
}