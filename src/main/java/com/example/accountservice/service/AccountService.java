package com.example.accountservice.service;

import com.example.accountservice.model.Account;
import com.example.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;


@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account createAccount(Long userId, String currency) {
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setCurrency(currency);
        // Логика для создания аккаунта
        return accountRepository.save(account);
    }

    @Transactional
    public Account deposit(Long accountId, BigDecimal amount) throws AccountNotFoundException {
        Account account = accountRepository.findByIdForUpdate(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Аккаует с id " + accountId + " не найден"));

        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }
}