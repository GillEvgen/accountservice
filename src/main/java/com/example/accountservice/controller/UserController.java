package com.example.accountservice.controller;


import com.example.accountservice.dto.UserDto;
import com.example.accountservice.exception.UserNotFoundException;
import com.example.accountservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Получение пользователя по ID
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) throws UserNotFoundException {
        return userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с  id " + userId + " не найден"));
    }

    // Получение пользователя по номеру документа
    @GetMapping("/document/{documentNumber}")
    public UserDto getUserByDocument(@PathVariable String documentNumber) throws UserNotFoundException {
        return userService.getUserByDocument(documentNumber)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с таким номером документа " + documentNumber + " не найден"));
    }

    // Создание нового пользователя
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody UserDto userDTO) {
        return userService.createUser(userDTO);
    }
}
