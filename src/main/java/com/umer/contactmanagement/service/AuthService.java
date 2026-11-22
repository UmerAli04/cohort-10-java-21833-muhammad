package com.umer.contactmanagement.service;

import com.umer.contactmanagement.dto.AuthResponse;
import com.umer.contactmanagement.dto.ChangePasswordRequest;
import com.umer.contactmanagement.dto.LoginRequest;
import com.umer.contactmanagement.dto.RegisterRequest;
import com.umer.contactmanagement.entity.User;
import com.umer.contactmanagement.exception.DuplicateResourceException;
import com.umer.contactmanagement.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {

        String loginIdentifier = request.getLoginIdentifier().trim();

        if (userRepository.findByLoginIdentifier(loginIdentifier).isPresent()) {
            throw new DuplicateResourceException(
                    "An account with this email or phone number already exists.");
        }

        User user = new User();

        user.setFullName(request.getFullName().trim());
        user.setLoginIdentifier(loginIdentifier);
        user.setIdentifierType(request.getIdentifierType().toUpperCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(
                savedUser.getLoginIdentifier());

        return new AuthResponse(
                token,
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getLoginIdentifier(),
                savedUser.getIdentifierType());
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository
                .findByLoginIdentifier(request.getLoginIdentifier().trim())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email/phone number or password."));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email/phone number or password.");
        }

        String token = jwtService.generateToken(
                user.getLoginIdentifier());

        return new AuthResponse(
                token,
                user.getId(),
                user.getFullName(),
                user.getLoginIdentifier(),
                user.getIdentifierType());
    }

    @Transactional
    public void changePassword(
            String loginIdentifier,
            ChangePasswordRequest request) {

        User user = userRepository
                .findByLoginIdentifier(loginIdentifier)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User account not found."));

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Current password is incorrect.");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }
}
