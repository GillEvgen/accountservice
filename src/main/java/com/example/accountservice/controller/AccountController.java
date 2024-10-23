package com.example.accountservice.controller;

import com.example.accountservice.dto.AccountDto;
import com.example.accountservice.exception.AccountNotFoundException;
import com.example.accountservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
@Validated
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Возвращаем JSON
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<AccountDto> getAllAccounts(Pageable pageable) {
        return accountService.getAllAccounts(pageable);
    }

    // Возвращаем JSON для получения аккаунта по ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDto getAccountById(@PathVariable("id") Long id) throws AccountNotFoundException, javax.security.auth.login.AccountNotFoundException {
        return accountService.getAccountById(id);
    }

    // Принимаем и возвращаем JSON при создании аккаунта
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto create(@Valid @RequestBody AccountDto accountDto) {
        return accountService.create(accountDto);
    }

    // Метод для пополнения баланса (как пример, если нужен)
    @PutMapping(value = "/{id}/deposit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AccountDto deposit(@PathVariable("id") Long id, @RequestParam BigDecimal amount) throws AccountNotFoundException, javax.security.auth.login.AccountNotFoundException {
        return accountService.deposit(id, amount);
    }
}
