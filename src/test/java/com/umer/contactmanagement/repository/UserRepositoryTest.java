package com.umer.contactmanagement.repository;

import com.umer.contactmanagement.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUserAndFindByLoginIdentifier() {

        User user = new User();
        user.setFullName("Ali Khan");
        user.setLoginIdentifier("ali.test@example.com");
        user.setIdentifierType("EMAIL");
        user.setPassword("encodedPassword123");

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByLoginIdentifier("ali.test@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("Ali Khan", foundUser.get().getFullName());
        assertEquals("EMAIL", foundUser.get().getIdentifierType());
    }
}
