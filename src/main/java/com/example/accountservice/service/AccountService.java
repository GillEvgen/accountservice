package com.example.accountservice.service;

import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.exception.UserNotFoundException;
import com.example.accountservice.mapper.AccountMapper;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.User;
import com.example.accountservice.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserService userService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.accountMapper = accountMapper;
    }

    // Получение всех аккаунтов с пагинацией
    public Page<AccountDto> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable)
                .map(accountMapper::toDto);  // Преобразование в DTO с помощью маппера
    }

    // Получение аккаунта по ID
    public Optional<Account> getAccountById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    // Пополнение баланса аккаунта
    @Transactional
    public AccountDto deposit(Long accountId, BigDecimal amount) throws AccountNotFoundException {
        Account account = accountRepository.findByIdForUpdate(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + accountId + " not found"));

        account.setBalance(account.getBalance().add(amount));  // Логика пополнения баланса
        accountRepository.save(account);  // Сохраняем изменения в базе данных
        return accountMapper.toDto(account);  // Преобразуем обратно в DTO и возвращаем
    }

    // Создание нового аккаунта
    @Transactional
    public AccountDto createAccount(Long userId, String currency) {
        User user = (User) userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        Account account = new Account();
        account.setUser(user);
        account.setCurrency(currency);
        account.setBalance(BigDecimal.ZERO);  // Начальный баланс

        Account savedAccount = accountRepository.save(account);  // Сохраняем новый аккаунт в базе
        return accountMapper.toDto(savedAccount);  // Преобразуем сущность в DTO и возвращаем
    }
}