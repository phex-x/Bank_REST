package com.example.bankcards.service;

import com.example.bankcards.dto.LoginRequest;
import com.example.bankcards.dto.RegistrationRequest;
import com.example.bankcards.dto.UserResponse;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.UserMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public LoginRequest createUser(RegistrationRequest registrationRequest) {
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new BadCredentialsException("user with email" + registrationRequest.getEmail() + "already exists");
        }
        User user = userMapper.toEntity(registrationRequest);
        user = userRepository.save(user);

        return userMapper.toLoginRequest(user);
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user with email" + email + " not found"));

        return user;
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user with id " + id + " not found"));

        return userMapper.toUserResponse(user);
    }

    public void disableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user with id " + id + " not found"));
        user.setIsEnabled(false);
        userRepository.save(user);
    }

    public void enableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user with id " + id + " not found"));
        user.setIsEnabled(true);
        userRepository.save(user);
    }

    public void changeRole(Long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("user with id " + userId + " not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("user with id " + id + " not found"));
        userRepository.delete(user);
    }
}
