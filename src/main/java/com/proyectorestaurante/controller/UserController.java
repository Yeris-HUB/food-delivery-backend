package com.proyectorestaurante.controller;

import com.proyectorestaurante.dto.UpdateUserRequest;
import com.proyectorestaurante.dto.UserDTO;
import com.proyectorestaurante.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }
    
    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateUserProfile(
            @RequestBody UpdateUserRequest updateRequest,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(userService.updateUserProfile(userId, updateRequest));
    }
}
