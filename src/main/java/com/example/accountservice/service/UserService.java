package com.example.accountservice.service;

import com.example.accountservice.model.User;
import com.example.accountservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByDocument(String documentNumber) {
        return userRepository.findByDocumentNumber(documentNumber);
    }
}