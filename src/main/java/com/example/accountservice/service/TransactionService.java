package com.example.accountservice.service;

import com.example.accountservice.dto.TransactionDto;
import com.example.accountservice.exception.TransactionNotFoundException;
import com.example.accountservice.mapper.TransactionMapper;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.Transaction;
import com.example.accountservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Transactional(readOnly = true)
    public Page<TransactionDto> getTransactionsByAccountId(Long accountId, Pageable pageable) {
        // Используем правильный тип Pageable
        Page<Transaction> transactionsPage = transactionRepository.findByAccountId(accountId, pageable);
        // Преобразование транзакций в DTO
        return transactionsPage.map(transactionMapper::toDto);
    }

    // Создание новой транзакции
    @Transactional
    public TransactionDto createTransaction(Long accountId, BigDecimal amount, String currency) throws AccountNotFoundException {
        // Получаем аккаунт напрямую
        Account account = (Account) accountService.getAccountById(accountId);

        // Проверяем, существует ли аккаунт
        if (account == null) {
            throw new AccountNotFoundException("Account with id " + accountId + " not found");
        }

        // Создаем транзакцию
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setTransactionDate(LocalDateTime.now());

        // Сохраняем транзакцию
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Возвращаем результат как Dto
        return transactionMapper.toDto(savedTransaction);
    }

    // Удаление транзакции
    @Transactional
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + transactionId + " not found"));

        transactionRepository.delete(transaction);
    }
}