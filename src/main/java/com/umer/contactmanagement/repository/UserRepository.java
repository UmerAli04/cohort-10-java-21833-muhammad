package com.umer.contactmanagement.repository;

import com.umer.contactmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginIdentifier(String loginIdentifier);

}
