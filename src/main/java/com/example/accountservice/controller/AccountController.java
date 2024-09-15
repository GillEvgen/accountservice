package com.example.accountservice.controller;

import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long accountId,
                                           @RequestParam BigDecimal amount) throws AccountNotFoundException {
        Account account = accountService.deposit(accountId, amount);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) throws AccountNotFoundException {
        return accountService.getAccountById(accountId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new AccountNotFoundException("Аккаунт с id " + accountId + " не найден"));
    }
}