package com.umer.contactmanagement.repository;

import com.umer.contactmanagement.entity.ContactEmail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactEmailRepository
        extends JpaRepository<ContactEmail, Long> {

    List<ContactEmail> findAllByContact_Id(Long contactId);

    void deleteAllByContact_Id(Long contactId);
}
