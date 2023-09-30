package com.example.groupservice.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Photo{
    private String small_file_id;
    private String small_file_unique_id;
    private String big_file_id;
    private String big_file_unique_id;
}
