package com.example.userservice.dto;

import com.example.userservice.validation.UniqueEmail;
import com.example.userservice.validation.UniqueName;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
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
