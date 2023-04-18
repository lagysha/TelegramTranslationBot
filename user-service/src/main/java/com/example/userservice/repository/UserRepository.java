package com.example.userservice.repository;

import com.example.userservice.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    User findByEmail(String username);
}
