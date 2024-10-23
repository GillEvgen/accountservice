package com.example.accountservice.controller;


import com.example.accountservice.dto.UserDto;
import com.example.accountservice.exception.UserNotFoundException;
import com.example.accountservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Получение всех пользователей с пагинацией, возвращаем JSON
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    // Получение пользователя по ID, возвращаем JSON
    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUserById(@PathVariable Long userId) throws UserNotFoundException {
        return userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    // Получение пользователя по номеру документа, возвращаем JSON
    @GetMapping(value = "/document/{documentNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto getUserByDocument(@PathVariable String documentNumber) throws UserNotFoundException {
        return userService.getUserByDocument(documentNumber)
                .orElseThrow(() -> new UserNotFoundException(documentNumber));
    }

    // Создание нового пользователя, принимаем и возвращаем JSON
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    // Удаление пользователя по ID
    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}
