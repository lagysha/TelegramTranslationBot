package com.example.authserver.service;

import com.example.authserver.dto.UserDto;
import com.example.authserver.mapper.UserMapper;
import com.example.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserDetailsManager userDetailsManager,
                       UserRepository userRepository,
                       UserMapper userMapper) {
        this.userDetailsManager = userDetailsManager;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void createNewUser(UserDto userDto) {
        userDetailsManager.createUser(userDto);
    }

    public UserDto findByEmail(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("user was not found: " + email));
        return userMapper.userToUserDto(user);
    }

    public void changePassword(String oldPassword, String newPassword) {
        userDetailsManager.changePassword(oldPassword, newPassword);
    }

    public void deleteAuthenticatedUser(String email) {
        userDetailsManager.deleteUser(email);
    }
}
