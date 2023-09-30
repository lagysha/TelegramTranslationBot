package com.example.userservice.service;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Spy
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    RequestUser testUserRequest;
    @BeforeEach
    public void populateUser(){
        testUserRequest = new RequestUser(1L,"user","N","M");
    }
    @Test
    @Order(1)
    @DisplayName("saveUser saves user by non-existing telegramUserId in DataBase")
    public void saveUser() {
        User user = userMapper.requestUserToUser(testUserRequest);
        Mockito.when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);

        UserDto actualUserDto = userService.saveUser(testUserRequest);

        assertEquals(userMapper.userToUserDto(user),actualUserDto);
    }

    @Test
    @Order(2)
    @DisplayName("saveGroupById does not save user by existing telegramUserId in DataBase")
    public void saveUserByExistingId() {
        Mockito.when(userRepository.saveAndFlush(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(
                DataIntegrityViolationException.class,
                () -> userService.saveUser(testUserRequest)
        );
    }
}
