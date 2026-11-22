package com.umer.contactmanagement.service;

import com.umer.contactmanagement.dto.*;
import com.umer.contactmanagement.entity.*;
import com.umer.contactmanagement.exception.ResourceNotFoundException;
import com.umer.contactmanagement.repository.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactEmailRepository emailRepository;
    private final ContactPhoneRepository phoneRepository;
    private final UserRepository userRepository;

    public ContactService(
            ContactRepository contactRepository,
            ContactEmailRepository emailRepository,
            ContactPhoneRepository phoneRepository,
            UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.emailRepository = emailRepository;
        this.phoneRepository = phoneRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ContactResponse createContact(
            Long userId,
            CreateContactRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User account not found."));

        Contact contact = new Contact();
        contact.setFirstName(request.getFirstName().trim());
        contact.setLastName(request.getLastName().trim());
        contact.setTitle(request.getTitle());
        contact.setUser(user);

        Contact savedContact = contactRepository.save(contact);

        saveEmails(savedContact, request.getEmails());
        savePhones(savedContact, request.getPhones());

        return mapToResponse(savedContact);
    }

    @Transactional(readOnly = true)
    public ContactPageResponse getContacts(
            Long userId,
            int page,
            int size) {
        validatePagination(page, size);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("firstName").ascending());

        Page<Contact> contactPage = contactRepository.findAllByUser_Id(userId, pageable);

        return mapToPageResponse(contactPage);
    }

    @Transactional(readOnly = true)
    public ContactPageResponse searchContacts(
            Long userId,
            String keyword,
            int page,
            int size) {
        validatePagination(page, size);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("firstName").ascending());

        Page<Contact> contactPage = contactRepository.searchByUserId(
                userId,
                keyword.trim(),
                pageable);

        return mapToPageResponse(contactPage);
    }

    @Transactional(readOnly = true)
    public ContactResponse getContactById(
            Long contactId,
            Long userId) {
        Contact contact = findContact(contactId, userId);

        return mapToResponse(contact);
    }

    @Transactional
    public ContactResponse updateContact(
            Long contactId,
            Long userId,
            UpdateContactRequest request) {
        Contact contact = findContact(contactId, userId);

        contact.setFirstName(request.getFirstName().trim());
        contact.setLastName(request.getLastName().trim());
        contact.setTitle(request.getTitle());

        Contact updatedContact = contactRepository.save(contact);

        emailRepository.deleteAllByContact_Id(contactId);
        phoneRepository.deleteAllByContact_Id(contactId);

        saveEmails(updatedContact, request.getEmails());
        savePhones(updatedContact, request.getPhones());

        return mapToResponse(updatedContact);
    }

    @Transactional
    public void deleteContact(Long contactId, Long userId) {

        Contact contact = findContact(contactId, userId);

        emailRepository.deleteAllByContact_Id(contactId);
        phoneRepository.deleteAllByContact_Id(contactId);

        contactRepository.delete(contact);
    }

    private Contact findContact(Long contactId, Long userId) {

        return contactRepository.findByIdAndUser_Id(contactId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contact not found."));
    }

    private void saveEmails(
            Contact contact,
            List<ContactEmailRequest> emails) {
        if (emails == null) {
            return;
        }

        for (ContactEmailRequest emailRequest : emails) {

            ContactEmail email = new ContactEmail();
            email.setLabel(emailRequest.getLabel().trim());
            email.setEmailAddress(emailRequest.getEmailAddress().trim());
            email.setContact(contact);

            emailRepository.save(email);
        }
    }

    private void savePhones(
            Contact contact,
            List<ContactPhoneRequest> phones) {
        if (phones == null) {
            return;
        }

        for (ContactPhoneRequest phoneRequest : phones) {

            ContactPhone phone = new ContactPhone();
            phone.setLabel(phoneRequest.getLabel().trim());
            phone.setPhoneNumber(phoneRequest.getPhoneNumber().trim());
            phone.setContact(contact);

            phoneRepository.save(phone);
        }
    }

    private ContactResponse mapToResponse(Contact contact) {

        ContactResponse response = new ContactResponse();

        response.setId(contact.getId());
        response.setFirstName(contact.getFirstName());
        response.setLastName(contact.getLastName());
        response.setTitle(contact.getTitle());
        response.setCreatedAt(contact.getCreatedAt());
        response.setUpdatedAt(contact.getUpdatedAt());

        List<ContactEmailResponse> emails = new ArrayList<>();

        for (ContactEmail email : emailRepository.findAllByContact_Id(contact.getId())) {

            emails.add(new ContactEmailResponse(
                    email.getId(),
                    email.getLabel(),
                    email.getEmailAddress()));
        }

        List<ContactPhoneResponse> phones = new ArrayList<>();

        for (ContactPhone phone : phoneRepository.findAllByContact_Id(contact.getId())) {

            phones.add(new ContactPhoneResponse(
                    phone.getId(),
                    phone.getLabel(),
                    phone.getPhoneNumber()));
        }

        response.setEmails(emails);
        response.setPhones(phones);

        return response;
    }

    private ContactPageResponse mapToPageResponse(Page<Contact> page) {

        List<ContactResponse> contacts = page.getContent()
                .stream()
                .map(this::mapToResponse)
                .toList();

        ContactPageResponse response = new ContactPageResponse();

        response.setContent(contacts);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());

        return response;
    }

    private void validatePagination(int page, int size) {

        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page number cannot be negative.");
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Page size must be between 1 and 100.");
        }
    }
}
