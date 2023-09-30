package com.example.userservice.repository;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT new com.example.userservice.dto." +
            "UserDto(u.id, u.telegramUserId, u.firstLoginDate, u.username, u.firstName,u.lastName)" +
            " FROM User u WHERE u.telegramUserId = :id")
    UserDto findByTelegramUserId(Long id);
}
