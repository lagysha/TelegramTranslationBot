package com.example.userservice.service.impl;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    public UserDto saveUser(RequestUser requestUser) {
        var user = userMapper.requestUserToUser(requestUser);
        var persistedUser = userRepository.saveAndFlush(user);
        return userMapper.userToUserDto(persistedUser);
    }

    @Transactional
    public UserDto getUserByTelegramId(Long id) {
        return userRepository.findByTelegramUserId(id);
    }
}
