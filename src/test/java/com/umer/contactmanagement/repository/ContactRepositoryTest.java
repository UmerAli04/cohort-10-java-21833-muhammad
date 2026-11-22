package com.umer.contactmanagement.repository;

import com.umer.contactmanagement.entity.Contact;
import com.umer.contactmanagement.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveContactAndFindByUserSuccessfully() {

        // Create and save a test user
        User user = new User();
        user.setFullName("Ali Khan");
        user.setLoginIdentifier("ali.contact@example.com");
        user.setIdentifierType("EMAIL");
        user.setPassword("encodedPassword123");

        User savedUser = userRepository.save(user);

        // Create and save a contact for this user
        Contact contact = new Contact();
        contact.setFirstName("Ahmed");
        contact.setLastName("Raza");
        contact.setTitle("Software Engineer");
        contact.setUser(savedUser);

        Contact savedContact = contactRepository.save(contact);

        // Find contact using its ID and owner's ID
        var foundContact = contactRepository.findByIdAndUser_Id(
                savedContact.getId(),
                savedUser.getId());

        assertTrue(foundContact.isPresent());
        assertEquals("Ahmed", foundContact.get().getFirstName());

        // Get all contacts belonging to this user
        Page<Contact> contacts = contactRepository.findAllByUser_Id(
                savedUser.getId(),
                PageRequest.of(0, 10));

        assertEquals(1, contacts.getTotalElements());

        // Search contacts using a keyword
        Page<Contact> searchResults = contactRepository.searchByUserId(
                savedUser.getId(),
                "Ahmed",
                PageRequest.of(0, 10));

        assertEquals(1, searchResults.getTotalElements());
        assertEquals("Ahmed", searchResults.getContent().get(0).getFirstName());
    }
}
