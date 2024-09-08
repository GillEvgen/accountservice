package com.example.accountservice.controller;
import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestParam Long userId, @RequestParam String currency) {
        Account account = accountService.createAccount(userId, currency);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long id,
                                           @RequestParam BigDecimal amount,
                                           @RequestParam String currency) {
        Account updatedAccount = accountService.deposit(id, amount, currency);
        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Optional<Account> account = accountService.getAccountById(id);
        return account.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
