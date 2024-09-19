package com.example.accountservice.controller;

import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Получение информации об аккаунте по ID
    @GetMapping("/{accountId}")
    public AccountDto getAccountById(@PathVariable Long accountId) throws AccountNotFoundException {
        return accountService.getAccountById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Аккаунт с id " + accountId + " not found"));
    }

    // Пополнение баланса аккаунта
    @PostMapping("/{accountId}/deposit")
    public AccountDto deposit(@PathVariable Long accountId, @RequestParam BigDecimal amount) throws AccountNotFoundException {
        return accountService.deposit(accountId, amount);
    }

    // Создание нового аккаунта
    @PostMapping("/create")
    public AccountDto createAccount(@Valid @RequestBody AccountDto accountDTO) {
        return accountService.createAccount(accountDTO.getUserId(), accountDTO.getCurrency());
    }
}