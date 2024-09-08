package com.example.accountservice.service;

import com.example.accountservice.model.Account;
import com.example.accountservice.model.Transaction;
import com.example.accountservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public Transaction createTransaction(Long accountId, BigDecimal amount, String currency) {
        Account account = accountService.getAccountById(accountId)
                .orElseThrow(() -> new RuntimeException("Аккаунт не найлен"));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setTransactionDate(LocalDateTime.now());

        accountService.deposit(accountId, amount, currency);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}