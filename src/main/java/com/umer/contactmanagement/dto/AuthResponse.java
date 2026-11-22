package com.umer.contactmanagement.dto;

public class AuthResponse {

    private String token;
    private Long userId;
    private String fullName;
    private String loginIdentifier;
    private String identifierType;

    public AuthResponse() {
    }

    public AuthResponse(
            String token,
            Long userId,
            String fullName,
            String loginIdentifier,
            String identifierType) {
        this.token = token;
        this.userId = userId;
        this.fullName = fullName;
        this.loginIdentifier = loginIdentifier;
        this.identifierType = identifierType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLoginIdentifier() {
        return loginIdentifier;
    }

    public void setLoginIdentifier(String loginIdentifier) {
        this.loginIdentifier = loginIdentifier;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }
}
