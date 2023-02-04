package com.example.userservice.repository;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT new com.example.userservice.dto." +
            "UserDto(u.id, u.telegramUserId, u.firstLoginDate, u.username, u.firstName,u. lastName, u.nextAction)" +
            " FROM User u WHERE u.telegramUserId = :id")
    UserDto findByTelegramUserId(Long id);

    User findEntityByTelegramUserId(Long id);

    @Query("SELECT new com.example.userservice.dto." +
            "UserDto(u.id, u.telegramUserId, u.firstLoginDate, u.username, u.firstName,u. lastName, u.nextAction)" +
            " FROM User u WHERE u.id = :id")
    UserDto findBy(@Param("id") Long id);
}
