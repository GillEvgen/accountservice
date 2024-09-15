package com.example.accountservice.service;

import com.example.accountservice.model.Account;
import com.example.accountservice.model.Transaction;
import com.example.accountservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {


    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Transactional
    public Transaction createTransaction(Long accountId, BigDecimal amount, String currency) throws AccountNotFoundException {
        Account account = accountService.getAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Аккаует с  id " + accountId + " ек найден"));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setTransactionDate(LocalDateTime.now());

        accountService.deposit(accountId, amount);

        return transactionRepository.save(transaction);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}