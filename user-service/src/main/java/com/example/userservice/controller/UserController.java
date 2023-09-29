package com.example.userservice.controller;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;
    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody RequestUser user) {
        var userDto = userServiceImpl.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDto);
    }
    @GetMapping(params = "telegramId")
    public ResponseEntity<UserDto> getUserByTelegramId(@RequestParam Long telegramId) {
        var userDto = userServiceImpl.getUserByTelegramId(telegramId);
        return ResponseEntity.ok(userDto);
    }
}
