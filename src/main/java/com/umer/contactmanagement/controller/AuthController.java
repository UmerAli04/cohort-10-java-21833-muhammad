package com.umer.contactmanagement.controller;

import com.umer.contactmanagement.dto.AuthResponse;
import com.umer.contactmanagement.dto.ChangePasswordRequest;
import com.umer.contactmanagement.dto.LoginRequest;
import com.umer.contactmanagement.dto.RegisterRequest;
import com.umer.contactmanagement.entity.User;
import com.umer.contactmanagement.service.AuthService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(
                user.getLoginIdentifier(),
                request);

        return ResponseEntity.ok(
                Map.of("message", "Password changed successfully."));
    }
}
