package com.example.accountservice.service;

import com.example.accountservice.dto.TransactionDto;
import com.example.accountservice.exception.TransactionNotFoundException;
import com.example.accountservice.mapper.TransactionMapper;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.Transaction;
import com.example.accountservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionMapper = transactionMapper;
    }

    // Получение транзакций для аккаунта
    public List<TransactionDto> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(transactionMapper::toDto)  // Преобразование сущности в DTO
                .toList();
    }

    // Создание новой транзакции
    @Transactional
    public TransactionDto createTransaction(Long accountId, BigDecimal amount, String currency) throws AccountNotFoundException {
        Account account = accountService.getAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + accountId + " not found"));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setTransactionDate(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);  // Сохраняем транзакцию
        return transactionMapper.toDto(savedTransaction);  // Преобразуем сущность в DTO и возвращаем
    }

    // Удаление транзакции
    @Transactional
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + transactionId + " not found"));

        transactionRepository.delete(transaction);
    }
}