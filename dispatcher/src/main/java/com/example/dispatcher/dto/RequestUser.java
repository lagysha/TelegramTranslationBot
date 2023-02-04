package com.example.dispatcher.dto;

import com.example.dispatcher.controller.enums.NextAction;
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

    private String firstName;

    private String lastName;

    private NextAction nextAction;

    public RequestUser(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
