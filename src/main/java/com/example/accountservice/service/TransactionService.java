package com.example.accountservice.service;

import com.example.accountservice.dto.TransactionDto;
import com.example.accountservice.exception.TransactionNotFoundException;
import com.example.accountservice.mapper.TransactionMapper;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.Transaction;
import com.example.accountservice.type.TransactionType;
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

    public TransactionDto deposit(Long accountId, BigDecimal amount) throws AccountNotFoundException {
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setAmount(amount);
        transaction.setCurrency("USD");  // Пример
        transaction.getTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        // Обновляем баланс аккаунта
        accountService.updateBalance(accountId, amount);

        return transactionMapper.toDto(transaction);
    }

    public TransactionDto transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        // Логика снятия средств с одного аккаунта и зачисления на другой

        Transaction withdrawal = new Transaction();
        withdrawal.setAccountId(fromAccountId);
        withdrawal.setAmount(amount.negate());  // Отрицательная сумма для снятия
        withdrawal.setTransactionType(TransactionType.TRANSFER);
        withdrawal.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(withdrawal);

        Transaction deposit = new Transaction();
        deposit.setAccountId(toAccountId);
        deposit.setAmount(amount);
        deposit.setTransactionType(TransactionType.TRANSFER);
        deposit.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(deposit);

        return transactionMapper.toDto(deposit);
    }

    @Transactional
    public TransactionDto withdraw(Long accountId, BigDecimal amount) throws AccountNotFoundException, IllegalArgumentException {
        // Создаем объект транзакции
        Transaction transaction = new Transaction();
        transaction.setAccountId(accountId);
        transaction.setAmount(amount.negate());  // Отрицательная сумма для снятия
        transaction.setCurrency("USD");  // Пример валюты, может быть параметром метода
        transaction.setTransactionType(TransactionType.WITHDRAWAL);  // Тип транзакции - снятие
        transaction.setTransactionDate(LocalDateTime.now());

        // Сохраняем транзакцию в базе данных
        transactionRepository.save(transaction);

        // Обновляем баланс аккаунта с отрицательной суммой (снимаем средства)
        accountService.updateBalance(accountId, amount.negate());

        // Возвращаем DTO транзакции
        return transactionMapper.toDto(transaction);
    }

    // Удаление транзакции
    @Transactional
    public void deleteTransaction(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id " + transactionId + " not found"));

        transactionRepository.delete(transaction);
    }
}