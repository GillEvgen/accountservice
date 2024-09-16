package com.example.accountservice.controller;


import com.example.accountservice.dto.UserDTO;
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

    // Получение пользователя по ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        Optional<User> userDto = userService.getUserById(userId);
        return userDto.map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
    }

    // Получение пользователя по номеру документа
    @GetMapping("/document/{documentNumber}")
    public ResponseEntity<User> getUserByDocument(@PathVariable String documentNumber) {
        Optional<User> userDto = userService.getUserByDocument(documentNumber);
        return userDto.map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("User with document number " + documentNumber + " not found"));
    }

    // Создание нового пользователя
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody User userDTO) {
        UserDTO newUser = userService.createUser(userDTO);
        return ResponseEntity.ok(newUser);
    }
}
