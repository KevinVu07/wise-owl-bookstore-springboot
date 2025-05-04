package com.wiseowl.bookstore.repository;

import com.wiseowl.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // to check for existing users
}
