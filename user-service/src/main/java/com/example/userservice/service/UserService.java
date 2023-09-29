package com.example.userservice.service;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.UserDto;

public interface UserService {
    UserDto saveUser(RequestUser requestUser);
    UserDto getUserByTelegramId(Long id);
}
