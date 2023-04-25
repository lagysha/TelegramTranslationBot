package com.example.userservice.dto;

import com.example.userservice.validation.UniqueEmail;
import com.example.userservice.validation.UniqueName;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private Long id;

    @NotBlank(message = "username can't be blank")
    @UniqueName
    private String username;

    @UniqueEmail
    @NotBlank(message = "email can't be blank")
    private String email;

    @NotBlank(message = "password can't be blank")
    private String password;
}
