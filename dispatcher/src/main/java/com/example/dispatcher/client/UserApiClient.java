package com.example.dispatcher.client;

import com.example.dispatcher.dto.RequestUser;
import com.example.dispatcher.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "user-service",
        path = "/users"
)
public interface UserApiClient {

    @PostMapping()
    ResponseEntity<UserDto> saveUser(@RequestBody RequestUser user);

    @GetMapping()
    ResponseEntity<UserDto> getUserByTelegramId(@RequestParam Long telegramId);

    @GetMapping()
    ResponseEntity<UserDto> getUserById(@RequestParam Long id);

    @GetMapping("/unban")
    ResponseEntity<String> unbanUser(@RequestParam String groupName,@RequestParam Long userId);

    @GetMapping("/verifyBotStatus")
    ResponseEntity<String> verifyBotStatus(@RequestParam String groupName);

    @PostMapping("/update")
    UserDto updateUser(@Valid @RequestBody RequestUser user);
}
