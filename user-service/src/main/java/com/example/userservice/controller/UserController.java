package com.example.userservice.controller;

import com.example.userservice.dto.RequestUser;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody RequestUser user) {
        var userDto = userService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userDto);
    }

    @PostMapping("/update")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody RequestUser user) {
        var userDto = userService.updateUser(user);
        System.out.println(userDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto);
    }

    @GetMapping(params = "id")
    public ResponseEntity<UserDto> getUserById(@RequestParam Long id) {
        var userDto = userService.getUser(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping(params = "telegramId")
    public ResponseEntity<UserDto> getUserByTelegramId(@RequestParam Long telegramId) {
        var userDto = userService.getUserByTelegramId(telegramId);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/unban")
    public ResponseEntity<String> unbanUser(@RequestParam String groupName,@RequestParam Long userId) {
        return ResponseEntity.ok(userService.unbanUser(groupName,userId));
    }

    @GetMapping("/verifyBotStatus")
    public ResponseEntity<String> verifyBotStatus(@RequestParam String groupName) {
        return ResponseEntity.ok(userService.verifyBotStatus(groupName));
    }
}
