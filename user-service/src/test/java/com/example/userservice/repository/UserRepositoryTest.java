package com.example.userservice.repository;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.mapper.UserMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Test
    @Order(1)
    @DisplayName("findByTelegramUserId returns groupDto by existing id")
    public void findByTelegramUserId(){
        User user  = new User();
        user.setTelegramUserId(1L);
        user.setUsername("N");
        User savedUser = userRepository.save(user);
        UserDto retrivedUserDto = userRepository.findByTelegramUserId(1L);
        assertEquals(userMapper.userToUserDto(savedUser),retrivedUserDto);
        userRepository.deleteById(1L);
    }

    @Test
    @Order(2)
    @DisplayName("findByTelegramUserId does not return groupDto by non-existing id")
    public void findByTelegramNonExistingUserId(){
        User user  = new User();
        user.setTelegramUserId(1L);
        user.setUsername("N");
        userRepository.save(user);
        assertNull(userRepository.findByTelegramUserId(2L));
    }
}
