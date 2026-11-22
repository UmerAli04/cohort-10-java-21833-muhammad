package com.umer.contactmanagement.dto;

public class ContactEmailResponse {

    private Long id;
    private String label;
    private String emailAddress;

    public ContactEmailResponse() {
    }

    public ContactEmailResponse(Long id, String label, String emailAddress) {
        this.id = id;
        this.label = label;
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
