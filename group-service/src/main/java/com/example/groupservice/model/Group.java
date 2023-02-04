package com.example.groupservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("group")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Group {
    @Id
    private String id;
    private boolean ok;
    private Result result;
    //add mapping dto to this field
    private Long adminId;
}
