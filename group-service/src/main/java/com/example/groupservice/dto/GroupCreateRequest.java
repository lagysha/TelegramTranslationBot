package com.example.groupservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupCreateRequest {
    private Long groupId;
    private Long adminId;
}
