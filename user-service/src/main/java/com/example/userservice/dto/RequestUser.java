package com.example.userservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUser {

    @NotNull
    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String firstName;

    private String lastName;
}
