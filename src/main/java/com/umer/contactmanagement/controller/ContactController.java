package com.umer.contactmanagement.controller;

import com.umer.contactmanagement.dto.ContactPageResponse;
import com.umer.contactmanagement.dto.ContactResponse;
import com.umer.contactmanagement.dto.CreateContactRequest;
import com.umer.contactmanagement.dto.UpdateContactRequest;
import com.umer.contactmanagement.entity.User;
import com.umer.contactmanagement.service.ContactService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ContactResponse> createContact(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateContactRequest request) {
        ContactResponse response = contactService.createContact(
                user.getId(),
                request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ContactPageResponse> getContacts(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ContactPageResponse response = contactService.getContacts(
                user.getId(),
                page,
                size);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ContactPageResponse> searchContacts(
            @AuthenticationPrincipal User user,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ContactPageResponse response = contactService.searchContacts(
                user.getId(),
                keyword,
                page,
                size);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getContactById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        ContactResponse response = contactService.getContactById(
                id,
                user.getId());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactResponse> updateContact(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody UpdateContactRequest request) {
        ContactResponse response = contactService.updateContact(
                id,
                user.getId(),
                request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        contactService.deleteContact(id, user.getId());

        return ResponseEntity.noContent().build();
    }
}
