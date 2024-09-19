package com.example.accountservice.service;

import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.exception.UserNotFoundException;
import com.example.accountservice.model.Account;
import com.example.accountservice.model.User;
import com.example.accountservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;


@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    // Маппинг сущности в DTO
    private AccountDto convertToDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setBalance(account.getBalance());
        dto.setCurrency(account.getCurrency());
        dto.setUserId(account.getUser().getId());
        return dto;
    }

    // Маппинг DTO в сущность
    private Account convertToEntity(AccountDto accountDTO, User user) {
        Account account = new Account();
        account.setBalance(accountDTO.getBalance());
        account.setCurrency(accountDTO.getCurrency());
        account.setUser(user);
        return account;
    }

    // Получение аккаунта по ID
    public Optional<AccountDto> getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .map(this::convertToDto); // Преобразуем сущность в DTO
    }

    // Пополнение баланса аккаунта
    public AccountDto deposit(Long accountId, BigDecimal amount) throws AccountNotFoundException {
        Account account = accountRepository.findByIdForUpdate(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Аккаунт с  " + accountId + " не найдке"));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account); // Сохраняем изменения в БД
        return convertToDto(account); // Возвращаем DTO
    }

    // Создание нового аккаунта
    public AccountDto createAccount(Long userId, String currency) {
        User user = userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с id " + userId + " не найдкен"));

        Account account = new Account();
        account.setBalance(BigDecimal.ZERO); // Начальное значение баланса
        account.setCurrency(currency);
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);
        return convertToDto(savedAccount); // Возвращаем DTO
    }
}