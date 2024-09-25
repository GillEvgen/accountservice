package com.example.accountservice.service;

import com.example.accountservice.dto.TransactionDto;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.Transaction;
import com.example.accountservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    // Маппинг сущности в DTO
    private TransactionDto convertToDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setAmount(transaction.getAmount());
        dto.setCurrency(transaction.getCurrency());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setAccountId(transaction.getAccount().getId());
        return dto;
    }

    // Получение транзакций для аккаунта
    public List<TransactionDto> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId)
                .stream()
                .map(this::convertToDto) // Преобразуем сущности в DTO
                .collect(Collectors.toList());
    }

    // Создание новой транзакции
    public TransactionDto createTransaction(Long accountId, BigDecimal amount, String currency) throws AccountNotFoundException {
        Account account = (Account) accountService.getAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Аккаунт с  id " + accountId + " не найлен"));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setTransactionDate(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDto(savedTransaction); // Возвращаем DTO
    }
}