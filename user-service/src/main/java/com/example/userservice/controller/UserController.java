package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto dto = userService.getUserByUsername(username);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto dto = userService.createUser(userDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }
}
