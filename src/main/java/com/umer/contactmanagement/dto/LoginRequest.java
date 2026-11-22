package com.umer.contactmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Email or phone number is required")
    @Size(max = 255, message = "Login identifier cannot exceed 255 characters")
    private String loginIdentifier;

    @NotBlank(message = "Password is required")
    private String password;

    public LoginRequest() {
    }

    public String getLoginIdentifier() {
        return loginIdentifier;
    }

    public void setLoginIdentifier(String loginIdentifier) {
        this.loginIdentifier = loginIdentifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
