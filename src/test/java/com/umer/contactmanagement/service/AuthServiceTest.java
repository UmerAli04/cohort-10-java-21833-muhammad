package com.umer.contactmanagement.service;

import com.umer.contactmanagement.dto.AuthResponse;
import com.umer.contactmanagement.dto.ChangePasswordRequest;
import com.umer.contactmanagement.dto.LoginRequest;
import com.umer.contactmanagement.dto.RegisterRequest;
import com.umer.contactmanagement.entity.User;
import com.umer.contactmanagement.exception.DuplicateResourceException;
import com.umer.contactmanagement.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

        @Mock
        private UserRepository userRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @Mock
        private JwtService jwtService;

        @InjectMocks
        private AuthService authService;

        @Test
        void registerUserSuccessfully() {

                RegisterRequest request = new RegisterRequest();

                request.setFullName("Ali Khan");
                request.setLoginIdentifier("ali@example.com");
                request.setIdentifierType("EMAIL");
                request.setPassword("Password123!");

                when(userRepository.findByLoginIdentifier("ali@example.com"))
                                .thenReturn(Optional.empty());

                when(passwordEncoder.encode("Password123!"))
                                .thenReturn("encoded-password");

                when(userRepository.save(any(User.class)))
                                .thenAnswer(invocation -> {
                                        User user = invocation.getArgument(0);
                                        user.setId(1L);
                                        return user;
                                });

                when(jwtService.generateToken("ali@example.com"))
                                .thenReturn("test-jwt-token");

                AuthResponse response = authService.register(request);

                assertNotNull(response);
                assertEquals(1L, response.getUserId());
                assertEquals("Ali Khan", response.getFullName());
                assertEquals("test-jwt-token", response.getToken());

                verify(userRepository).save(any(User.class));
                verify(passwordEncoder).encode("Password123!");
        }

        @Test
        void registerUserWithExistingEmail() {

                RegisterRequest request = new RegisterRequest();

                request.setFullName("Ali Khan");
                request.setLoginIdentifier("ali@example.com");
                request.setIdentifierType("EMAIL");
                request.setPassword("Password123!");

                when(userRepository.findByLoginIdentifier("ali@example.com"))
                                .thenReturn(Optional.of(new User()));

                assertThrows(
                                DuplicateResourceException.class,
                                () -> authService.register(request));

                verify(userRepository, never()).save(any(User.class));
        }

        @Test
        void loginUserSuccessfully() {

                LoginRequest request = new LoginRequest();
                request.setLoginIdentifier("ali@example.com");
                request.setPassword("Password123!");

                User user = new User();
                user.setId(1L);
                user.setFullName("Ali Khan");
                user.setLoginIdentifier("ali@example.com");
                user.setIdentifierType("EMAIL");
                user.setPassword("encoded-password");

                when(userRepository.findByLoginIdentifier("ali@example.com"))
                                .thenReturn(Optional.of(user));

                when(passwordEncoder.matches("Password123!", "encoded-password"))
                                .thenReturn(true);

                when(jwtService.generateToken("ali@example.com"))
                                .thenReturn("test-jwt-token");

                AuthResponse response = authService.login(request);

                assertNotNull(response);
                assertEquals(1L, response.getUserId());
                assertEquals("Ali Khan", response.getFullName());
                assertEquals("test-jwt-token", response.getToken());

                verify(passwordEncoder).matches("Password123!", "encoded-password");
                verify(jwtService).generateToken("ali@example.com");
        }

        @Test
        void loginWithIncorrectPassword() {

                LoginRequest request = new LoginRequest();
                request.setLoginIdentifier("ali@example.com");
                request.setPassword("WrongPassword123!");

                User user = new User();
                user.setId(1L);
                user.setFullName("Ali Khan");
                user.setLoginIdentifier("ali@example.com");
                user.setIdentifierType("EMAIL");
                user.setPassword("encoded-password");

                when(userRepository.findByLoginIdentifier("ali@example.com"))
                                .thenReturn(Optional.of(user));

                when(passwordEncoder.matches(
                                "WrongPassword123!", "encoded-password"))
                                .thenReturn(false);

                assertThrows(
                                org.springframework.web.server.ResponseStatusException.class,
                                () -> authService.login(request));

                verify(jwtService, never()).generateToken(any());
        }

        @Test
        void changePasswordSuccessfully() {

                ChangePasswordRequest request = new ChangePasswordRequest();
                request.setCurrentPassword("Password123!");
                request.setNewPassword("NewPassword123!");

                User user = new User();
                user.setId(1L);
                user.setFullName("Ali Khan");
                user.setLoginIdentifier("ali@example.com");
                user.setPassword("encoded-password");

                when(userRepository.findByLoginIdentifier("ali@example.com"))
                                .thenReturn(Optional.of(user));

                when(passwordEncoder.matches("Password123!", "encoded-password"))
                                .thenReturn(true);

                when(passwordEncoder.encode("NewPassword123!"))
                                .thenReturn("new-encoded-password");

                authService.changePassword("ali@example.com", request);

                assertEquals("new-encoded-password", user.getPassword());

                verify(passwordEncoder).encode("NewPassword123!");
                verify(userRepository).save(user);
        }

        @Test
        void changePasswordWithIncorrectCurrentPassword() {

                ChangePasswordRequest request = new ChangePasswordRequest();
                request.setCurrentPassword("WrongPassword123!");
                request.setNewPassword("NewPassword123!");

                User user = new User();
                user.setId(1L);
                user.setLoginIdentifier("ali@example.com");
                user.setPassword("encoded-password");

                when(userRepository.findByLoginIdentifier("ali@example.com"))
                                .thenReturn(Optional.of(user));

                when(passwordEncoder.matches(
                                "WrongPassword123!", "encoded-password"))
                                .thenReturn(false);

                assertThrows(
                                org.springframework.web.server.ResponseStatusException.class,
                                () -> authService.changePassword("ali@example.com", request));

                verify(passwordEncoder, never()).encode(any());
                verify(userRepository, never()).save(any(User.class));
        }

        @Test
        void loginWithNonExistingUser() {

                LoginRequest request = new LoginRequest();
                request.setLoginIdentifier("unknown@example.com");
                request.setPassword("Password123!");

                when(userRepository.findByLoginIdentifier("unknown@example.com"))
                                .thenReturn(Optional.empty());

                assertThrows(
                                org.springframework.web.server.ResponseStatusException.class,
                                () -> authService.login(request));

                verify(passwordEncoder, never()).matches(any(), any());
                verify(jwtService, never()).generateToken(any());
        }
}
