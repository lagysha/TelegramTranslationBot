package com.example.authserver.service;

import com.example.authserver.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDetailsManager userDetailsManager;

    @Autowired
    public UserService(UserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    public void createNewUser(UserDto userDto) {
        userDetailsManager.createUser(userDto);
    }
}
