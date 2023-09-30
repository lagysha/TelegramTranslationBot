package com.example.dispatcher.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUser {

    @NotNull
    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String firstName;

    private String lastName;
}
