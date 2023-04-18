package com.example.userservice.service;

import com.example.userservice.data.User;
import com.example.userservice.dto.UserDto;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto getUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(
                () -> new IllegalArgumentException(String.format("User with username %s doesn't exist", username))
        );

        return userMapper.userToUserDto(user);
    }
}