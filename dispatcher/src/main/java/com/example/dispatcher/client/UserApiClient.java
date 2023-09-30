package com.example.dispatcher.client;

import com.example.dispatcher.dto.RequestUser;
import com.example.dispatcher.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
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
    UserDto saveUser(@RequestBody RequestUser user);

    @GetMapping()
    UserDto getUserByTelegramId(@RequestParam Long telegramId);
}
