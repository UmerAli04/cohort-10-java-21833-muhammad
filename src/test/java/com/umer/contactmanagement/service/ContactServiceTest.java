package com.umer.contactmanagement.service;

import com.umer.contactmanagement.repository.ContactRepository;
import com.umer.contactmanagement.repository.ContactEmailRepository;
import com.umer.contactmanagement.repository.ContactPhoneRepository;
import com.umer.contactmanagement.repository.UserRepository;
import com.umer.contactmanagement.dto.ContactPageResponse;
import com.umer.contactmanagement.dto.ContactResponse;
import com.umer.contactmanagement.dto.CreateContactRequest;
import com.umer.contactmanagement.dto.UpdateContactRequest;
import com.umer.contactmanagement.entity.Contact;
import com.umer.contactmanagement.entity.User;
import com.umer.contactmanagement.exception.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactEmailRepository contactEmailRepository;

    @Mock
    private ContactPhoneRepository contactPhoneRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    void createContactSuccessfully() {

        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Ali");
        request.setLastName("Khan");
        request.setTitle("Software Engineer");
        request.setEmails(List.of());
        request.setPhones(List.of());

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(contactRepository.save(any(Contact.class)))
                .thenAnswer(invocation -> {
                    Contact contact = invocation.getArgument(0);
                    contact.setId(10L);
                    return contact;
                });

        when(contactEmailRepository.findAllByContact_Id(10L))
                .thenReturn(List.of());

        when(contactPhoneRepository.findAllByContact_Id(10L))
                .thenReturn(List.of());

        ContactResponse response = contactService.createContact(userId, request);

        assertNotNull(response);
        assertEquals(10L, response.getId());
        assertEquals("Ali", response.getFirstName());
        assertEquals("Khan", response.getLastName());
        assertEquals("Software Engineer", response.getTitle());

        verify(contactRepository).save(any(Contact.class));
    }

    @Test
    void createContactWithNonExistingUser() {

        Long userId = 99L;

        CreateContactRequest request = new CreateContactRequest();
        request.setFirstName("Ali");
        request.setLastName("Khan");
        request.setTitle("Software Engineer");
        request.setEmails(List.of());
        request.setPhones(List.of());

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> contactService.createContact(userId, request));

        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void getContactsSuccessfully() {

        Long userId = 1L;

        Contact contact = new Contact();
        contact.setId(10L);
        contact.setFirstName("Ali");
        contact.setLastName("Khan");
        contact.setTitle("Software Engineer");

        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by("firstName").ascending());
        Page<Contact> contactPage = new PageImpl<>(List.of(contact), pageable, 1);

        when(contactRepository.findAllByUser_Id(userId, pageable))
                .thenReturn(contactPage);

        when(contactEmailRepository.findAllByContact_Id(10L))
                .thenReturn(List.of());

        when(contactPhoneRepository.findAllByContact_Id(10L))
                .thenReturn(List.of());

        ContactPageResponse response = contactService.getContacts(userId, 0, 10);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getContent().size());
        assertEquals("Ali", response.getContent().get(0).getFirstName());
        assertEquals("Khan", response.getContent().get(0).getLastName());

        verify(contactRepository).findAllByUser_Id(userId, pageable);
    }

    @Test
    void getContactByIdSuccessfully() {

        Long userId = 1L;
        Long contactId = 10L;

        Contact contact = new Contact();
        contact.setId(contactId);
        contact.setFirstName("Ali");
        contact.setLastName("Khan");
        contact.setTitle("Software Engineer");

        when(contactRepository.findByIdAndUser_Id(contactId, userId))
                .thenReturn(Optional.of(contact));

        when(contactEmailRepository.findAllByContact_Id(contactId))
                .thenReturn(List.of());

        when(contactPhoneRepository.findAllByContact_Id(contactId))
                .thenReturn(List.of());

        ContactResponse response = contactService.getContactById(contactId, userId);

        assertNotNull(response);
        assertEquals(contactId, response.getId());
        assertEquals("Ali", response.getFirstName());
        assertEquals("Khan", response.getLastName());
        assertEquals("Software Engineer", response.getTitle());

        verify(contactRepository).findByIdAndUser_Id(contactId, userId);
    }

    @Test
    void getContactByIdWithNonExistingContact() {

        Long userId = 1L;
        Long contactId = 99L;

        when(contactRepository.findByIdAndUser_Id(contactId, userId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> contactService.getContactById(contactId, userId));

        verify(contactRepository).findByIdAndUser_Id(contactId, userId);

        verify(contactEmailRepository, never())
                .findAllByContact_Id(contactId);

        verify(contactPhoneRepository, never())
                .findAllByContact_Id(contactId);
    }

    @Test
    void updateContactSuccessfully() {

        Long userId = 1L;
        Long contactId = 10L;

        Contact contact = new Contact();
        contact.setId(contactId);
        contact.setFirstName("Ali");
        contact.setLastName("Khan");
        contact.setTitle("Developer");

        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("Ali");
        request.setLastName("Ahmed");
        request.setTitle("Senior Software Engineer");
        request.setEmails(List.of());
        request.setPhones(List.of());

        when(contactRepository.findByIdAndUser_Id(contactId, userId))
                .thenReturn(Optional.of(contact));

        when(contactRepository.save(any(Contact.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(contactEmailRepository.findAllByContact_Id(contactId))
                .thenReturn(List.of());

        when(contactPhoneRepository.findAllByContact_Id(contactId))
                .thenReturn(List.of());

        ContactResponse response = contactService.updateContact(contactId, userId, request);

        assertNotNull(response);
        assertEquals(contactId, response.getId());
        assertEquals("Ali", response.getFirstName());
        assertEquals("Ahmed", response.getLastName());
        assertEquals("Senior Software Engineer", response.getTitle());

        verify(contactRepository).save(contact);
    }

    @Test
    void updateContactWithNonExistingContact() {

        Long userId = 1L;
        Long contactId = 99L;

        UpdateContactRequest request = new UpdateContactRequest();
        request.setFirstName("Ali");
        request.setLastName("Ahmed");
        request.setTitle("Software Engineer");
        request.setEmails(List.of());
        request.setPhones(List.of());

        when(contactRepository.findByIdAndUser_Id(contactId, userId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> contactService.updateContact(contactId, userId, request));

        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void deleteContactSuccessfully() {

        Long userId = 1L;
        Long contactId = 10L;

        Contact contact = new Contact();
        contact.setId(contactId);
        contact.setFirstName("Ali");
        contact.setLastName("Khan");

        when(contactRepository.findByIdAndUser_Id(contactId, userId))
                .thenReturn(Optional.of(contact));

        contactService.deleteContact(contactId, userId);

        verify(contactEmailRepository).deleteAllByContact_Id(contactId);

        verify(contactPhoneRepository).deleteAllByContact_Id(contactId);

        verify(contactRepository).delete(contact);
    }

    @Test
    void deleteContactWithNonExistingContact() {

        Long userId = 1L;
        Long contactId = 99L;

        when(contactRepository.findByIdAndUser_Id(contactId, userId))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> contactService.deleteContact(contactId, userId));

        verify(contactRepository, never()).delete(any(Contact.class));

        verify(contactEmailRepository, never())
                .deleteAllByContact_Id(contactId);

        verify(contactPhoneRepository, never())
                .deleteAllByContact_Id(contactId);
    }

    @Test
    void searchContactsSuccessfully() {

        Long userId = 1L;
        String keyword = "Ali";

        Contact contact = new Contact();
        contact.setId(10L);
        contact.setFirstName("Ali");
        contact.setLastName("Khan");
        contact.setTitle("Software Engineer");

        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by("firstName").ascending());

        Page<Contact> contactPage = new PageImpl<>(List.of(contact), pageable, 1);

        when(contactRepository.searchByUserId(userId, keyword, pageable))
                .thenReturn(contactPage);

        when(contactEmailRepository.findAllByContact_Id(10L))
                .thenReturn(List.of());

        when(contactPhoneRepository.findAllByContact_Id(10L))
                .thenReturn(List.of());

        ContactPageResponse response = contactService.searchContacts(userId, keyword, 0, 10);

        assertNotNull(response);
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getContent().size());
        assertEquals("Ali", response.getContent().get(0).getFirstName());

        verify(contactRepository).searchByUserId(userId, keyword, pageable);
    }
}
