package com.example.accountservice.controller;

import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<Page<AccountDto>> getAllAccounts(Pageable pageable) {
        Page<AccountDto> accounts = accountService.getAllAccounts(pageable);
        return ResponseEntity.ok(accounts);
    }
    @GetMapping("/{id}")
    public AccountDto getAccountById(@PathVariable("id") Long id) throws AccountNotFoundException {
          return accountService.getAccountById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto create(@RequestBody AccountDto accountDto) {
        return accountService.create(accountDto);
    }

    @PutMapping("/{id}/deposit")
    public AccountDto deposit(@PathVariable("id") Long id, @RequestParam BigDecimal amount) throws AccountNotFoundException {
        return accountService.deposit(id, amount);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("accountId") Long accountId) throws AccountNotFoundException {
        accountService.delete(accountId);
    }

}
