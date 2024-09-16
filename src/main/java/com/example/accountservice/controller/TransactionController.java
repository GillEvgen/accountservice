package com.example.accountservice.controller;

import com.example.accountservice.dto.TransactionDTO;
import com.example.accountservice.exception.TransactionNotFoundException;
import com.example.accountservice.model.Transaction;
import com.example.accountservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Получение транзакций для аккаунта
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        if (transactions.isEmpty()) {
            throw new TransactionNotFoundException("Для счета с ID транзакций не найдено " + accountId);
        }
        return ResponseEntity.ok(transactions);
    }

    // Создание новой транзакции
    @PostMapping("/create")
    public ResponseEntity<TransactionDTO> createTransaction(
            @Valid @RequestBody TransactionDTO transactionDTO) throws AccountNotFoundException {

        TransactionDTO newTransaction = transactionService.createTransaction(
                transactionDTO.getAccountId(),
                transactionDTO.getAmount(),
                transactionDTO.getCurrency()
        );
        return ResponseEntity.ok(newTransaction);
    }
}