package com.example.accountservice.controller;


import com.example.accountservice.exception.UserNotFoundException;
import com.example.accountservice.model.User;
import com.example.accountservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Создание нового пользователя
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    // Получение пользователя по ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с  id " + id + " не найден"));
    }

    // Получение пользователя по номеру документа
    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<User> getUserByDocument(@PathVariable String documentNumber) {
        Optional<User> user = userService.getUserByDocument(documentNumber);
        return user.map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("Пользователь с номером документа  " + documentNumber + " не найлен"));
    }
}