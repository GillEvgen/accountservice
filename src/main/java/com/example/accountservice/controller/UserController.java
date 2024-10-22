package com.example.accountservice.controller;


import com.example.accountservice.dto.UserDto;
import com.example.accountservice.exception.UserNotFoundException;
import com.example.accountservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    // Получение всех пользователей с пагинацией
    @GetMapping
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    // Получение пользователя по ID
    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) throws UserNotFoundException {
        return userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    // Получение пользователя по номеру документа
    @GetMapping("/document/{documentNumber}")
    public UserDto getUserByDocument(@PathVariable String documentNumber) throws UserNotFoundException {
        return userService.getUserByDocument(documentNumber)
                .orElseThrow(() -> new UserNotFoundException(documentNumber));
    }

    // Создание нового пользователя
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

//    // Обновление данных пользователя
//    @PutMapping("/{userId}")
//    public UserDto updateUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
//        return userService.updateUser(userId, userDto);
//    }

    // Удаление пользователя
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        userService.delete(userId);
    }
}