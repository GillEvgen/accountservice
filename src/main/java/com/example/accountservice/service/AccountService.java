package com.example.accountservice.service;

import com.example.accountservice.model.Account;
import com.example.accountservice.model.User;
import com.example.accountservice.repository.AccountRepository;
import com.example.accountservice.repository.TransactionRepository;
import com.example.accountservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Transactional
    public Account createAccount(Long userId, String currency) {
        // Шаг 1: Проверяем, существует ли пользователь с данным ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Шаг 2: Создаем новый аккаунт с начальным балансом 0
        Account account = new Account();
        account.setUser(user);  // Связываем с пользователем
        account.setBalance(BigDecimal.ZERO);  // Устанавливаем начальный баланс 0
        account.setCurrency(currency);  // Устанавливаем валюту счета

        // Шаг 3: Сохраняем аккаунт в базе данных
        return accountRepository.save(account);
    }

    @Transactional
    public Account deposit(Long accountId, BigDecimal amount, String currency) {
        // Проверяем, что сумма пополнения положительная
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Депозит дожен быть больше чем ноль");
        }

        // Ищем аккаунт с блокировкой для записи, чтобы предотвратить проблемы с одновременным доступом
        Account account = accountRepository.findByIdForUpdate(accountId)
                .orElseThrow(() -> new RuntimeException("Аккаунт не найден"));

        // Обновляем баланс: прибавляем к текущему балансу сумму пополнения
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);

        // Сохраняем обновленный аккаунт в базе данных
        return accountRepository.save(account);
    }

    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) {
        // Проверяем, что сумма пополнения больше нуля
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Депозит дожен быть больше чем ноль");
        }

        // Находим аккаунт с блокировкой для записи, чтобы предотвратить проблемы с одновременным доступом
        Account account = accountRepository.findByIdForUpdate(accountId)
                .orElseThrow(() -> new RuntimeException("Акаунт не найден"));

        // Обновляем баланс: прибавляем к текущему балансу сумму пополнения
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);

        // Сохраняем обновленный аккаунт
        return accountRepository.save(account);
    }
}