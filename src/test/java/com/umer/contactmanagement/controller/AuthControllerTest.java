package com.umer.contactmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umer.contactmanagement.dto.AuthResponse;
import com.umer.contactmanagement.dto.LoginRequest;
import com.umer.contactmanagement.dto.RegisterRequest;
import com.umer.contactmanagement.service.AuthService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .build();

        objectMapper = new ObjectMapper();
    }

    @Test
    void registerUserSuccessfully() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setFullName("Ali Khan");
        request.setLoginIdentifier("ali@example.com");
        request.setIdentifierType("EMAIL");
        request.setPassword("Password123!");

        AuthResponse response = new AuthResponse(
                "test-jwt-token",
                1L,
                "Ali Khan",
                "ali@example.com",
                "EMAIL");

        when(authService.register(any(RegisterRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.fullName").value("Ali Khan"))
                .andExpect(jsonPath("$.token").value("test-jwt-token"));

        verify(authService).register(any(RegisterRequest.class));
    }

    @Test
    void loginUserSuccessfully() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setLoginIdentifier("ali@example.com");
        request.setPassword("Password123!");

        AuthResponse response = new AuthResponse(
                "test-jwt-token",
                1L,
                "Ali Khan",
                "ali@example.com",
                "EMAIL");

        when(authService.login(any(LoginRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.fullName").value("Ali Khan"))
                .andExpect(jsonPath("$.token").value("test-jwt-token"));

        verify(authService).login(any(LoginRequest.class));
    }
}
