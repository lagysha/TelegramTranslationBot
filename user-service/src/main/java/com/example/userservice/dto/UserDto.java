package com.example.userservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(telegramUserId, userDto.telegramUserId) && Objects.equals(username, userDto.username) && Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, telegramUserId, username, firstName, lastName);
    }
}