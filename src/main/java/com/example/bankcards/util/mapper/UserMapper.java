package com.example.bankcards.util.mapper;

import com.example.bankcards.dto.UserCreateDTO;
import com.example.bankcards.dto.UserResponseDTO;
import com.example.bankcards.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(userCreateDTO.getPassword());

        return user;
    }

    public UserResponseDTO toDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setRole(user.getRole());

        return userResponseDTO;
    }
}
