package com.example.accountservice.service;

import com.example.accountservice.dto.UserDto;
import com.example.accountservice.model.User;
import com.example.accountservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Инъекция через конструктор
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }

    private User convertToEntity(UserDto userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setDocumentNumber(userDTO.getDocumentNumber());
        user.setDocumentType(userDTO.getDocumentType());
        return user;
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setDocumentNumber(user.getDocumentNumber());
        dto.setDocumentType(user.getDocumentType());
        return dto;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByDocument(String documentNumber) {
        return userRepository.findByDocumentNumber(documentNumber);
    }
}