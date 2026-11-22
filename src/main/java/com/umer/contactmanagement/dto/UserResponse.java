package com.umer.contactmanagement.dto;

public class UserResponse {

    private Long id;
    private String fullName;
    private String loginIdentifier;
    private String identifierType;

    public UserResponse() {
    }

    public UserResponse(
            Long id,
            String fullName,
            String loginIdentifier,
            String identifierType) {
        this.id = id;
        this.fullName = fullName;
        this.loginIdentifier = loginIdentifier;
        this.identifierType = identifierType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
