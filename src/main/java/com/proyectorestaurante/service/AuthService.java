package com.proyectorestaurante.service;

import com.proyectorestaurante.dto.AuthResponse;
import com.proyectorestaurante.dto.LoginRequest;
import com.proyectorestaurante.dto.RegisterRequest;
import com.proyectorestaurante.dto.UserDTO;
import com.proyectorestaurante.model.User;
import com.proyectorestaurante.repository.UserRepository;
import com.proyectorestaurante.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        
        // Create new user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
        
        user = userRepository.save(user);
        
        // Generate JWT token
        String token = tokenProvider.generateToken(user.getEmail(), user.getId());
        
        // Create user DTO
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
        
        return new AuthResponse(token, userDTO);
    }
    
    public AuthResponse login(LoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        
        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        // Generate JWT token
        String token = tokenProvider.generateToken(user.getEmail(), user.getId());
        
        // Create user DTO
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .build();
        
        return new AuthResponse(token, userDTO);
    }
}
