package com.example.dispatcher.dto;

import com.example.dispatcher.controller.enums.NextAction;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    private Long id;

    @NotNull
    private Long telegramUserId;

    private LocalDateTime firstLoginDate;

    @NotEmpty
    private String username;

    private String firstName;

    private String lastName;

    private NextAction nextAction; // default value is NONE during persisting an entity
}
