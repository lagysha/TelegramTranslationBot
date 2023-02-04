package com.example.groupservice.dto;


import com.example.groupservice.model.Result;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GroupDto {
    private String id;
    private boolean ok;
    private Result result;
}
