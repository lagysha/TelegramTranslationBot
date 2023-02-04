package com.example.dispatcher.dto.group;

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
