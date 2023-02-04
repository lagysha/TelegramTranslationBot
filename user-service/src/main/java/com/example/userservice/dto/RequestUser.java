package com.example.userservice.dto;

import com.example.userservice.entity.enums.NextAction;
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

    private String firstName;

    private String lastName;

    private NextAction nextAction;
}
