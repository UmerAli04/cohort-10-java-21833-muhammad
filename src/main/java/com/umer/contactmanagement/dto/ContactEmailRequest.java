package com.umer.contactmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContactEmailRequest {

    @NotBlank(message = "Email label is required")
    @Size(max = 50, message = "Email label cannot exceed 50 characters")
    private String label;

    @NotBlank(message = "Email address is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 255, message = "Email address cannot exceed 255 characters")
    private String emailAddress;

    public ContactEmailRequest() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
