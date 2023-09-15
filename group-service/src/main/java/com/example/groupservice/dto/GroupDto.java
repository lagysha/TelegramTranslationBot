package com.example.groupservice.dto;


import com.example.groupservice.model.Result;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupDto {
    private String id;
    private boolean ok;
    private Result result;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        GroupDto groupDto = (GroupDto) object;
        return ok == groupDto.ok && Objects.equals(id, groupDto.id) && Objects.equals(result, groupDto.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ok, result);
    }
}
