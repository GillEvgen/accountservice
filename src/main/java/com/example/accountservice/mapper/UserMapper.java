package com.example.accountservice.mapper;


import com.example.accountservice.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Преобразование User в UserDTO
    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setDocumentNumber(user.getDocumentNumber());
        dto.setDocumentType(user.getDocumentType());
        return dto;
    }

    // Преобразование UserDTO в User
    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setDocumentNumber(dto.getDocumentNumber());
        user.setDocumentType(dto.getDocumentType());
        return user;
    }
}