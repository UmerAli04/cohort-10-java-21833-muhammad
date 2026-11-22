package com.umer.contactmanagement.repository;

import com.umer.contactmanagement.entity.ContactPhone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactPhoneRepository
        extends JpaRepository<ContactPhone, Long> {

    List<ContactPhone> findAllByContact_Id(Long contactId);

    void deleteAllByContact_Id(Long contactId);
}
