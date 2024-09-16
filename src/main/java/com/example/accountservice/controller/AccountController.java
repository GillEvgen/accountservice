package com.example.accountservice.controller;

import com.example.accountservice.dto.AccountDTO;
import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

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
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long accountId) throws AccountNotFoundException {
        Optional<AccountDTO> accountDto = accountService.getAccountById(accountId);
        return accountDto.map(ResponseEntity::ok)
                .orElseThrow(() -> new AccountNotFoundException("Account with id " + accountId + " not found"));
    }

    // Пополнение баланса аккаунта
    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountDTO> deposit(@PathVariable Long accountId, @RequestParam BigDecimal amount) throws AccountNotFoundException {
        AccountDTO updatedAccount = accountService.deposit(accountId, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    // Создание нового аккаунта
    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        Account newAccount = (Account) accountService.createAccount(accountDTO.getUserId(), accountDTO.getCurrency());
        return ResponseEntity.ok(newAccount);
    }
}