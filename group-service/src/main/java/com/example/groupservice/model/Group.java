package com.example.groupservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

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
    private Long adminId;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Group group = (Group) object;
        return ok == group.ok && Objects.equals(id, group.id) && Objects.equals(result, group.result) && Objects.equals(adminId, group.adminId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ok, result, adminId);
    }
}
