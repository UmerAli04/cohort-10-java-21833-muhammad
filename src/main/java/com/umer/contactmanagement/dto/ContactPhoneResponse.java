package com.umer.contactmanagement.dto;

public class ContactPhoneResponse {

    private Long id;
    private String label;
    private String phoneNumber;

    public ContactPhoneResponse() {
    }

    public ContactPhoneResponse(Long id, String label, String phoneNumber) {
        this.id = id;
        this.label = label;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
