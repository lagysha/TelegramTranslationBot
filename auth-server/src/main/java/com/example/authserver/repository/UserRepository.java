package com.example.authserver.repository;

import com.example.authserver.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findUserByUsername(String username);

    void deleteByEmail(String email);
}
