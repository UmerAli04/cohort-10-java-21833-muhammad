package com.umer.contactmanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateContactRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name cannot exceed 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name cannot exceed 100 characters")
    private String lastName;

    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    private List<@Valid ContactEmailRequest> emails;

    private List<@Valid ContactPhoneRequest> phones;

    public CreateContactRequest() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ContactEmailRequest> getEmails() {
        return emails;
    }

    public void setEmails(List<ContactEmailRequest> emails) {
        this.emails = emails;
    }

    public List<ContactPhoneRequest> getPhones() {
        return phones;
    }

    public void setPhones(List<ContactPhoneRequest> phones) {
        this.phones = phones;
    }
}
