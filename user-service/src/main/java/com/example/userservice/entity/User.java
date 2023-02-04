package com.example.userservice.entity;

import com.example.userservice.entity.enums.NextAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter @Setter @ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "telegram_user_id", unique = true)
    private Long telegramUserId;

    @Column(name = "first_login_date")
    @CreationTimestamp
    private LocalDateTime firstLoginDate;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "next_action", columnDefinition = "VARCHAR(20) DEFAULT 'NONE'")
    @Enumerated(EnumType.STRING)
    private NextAction nextAction = NextAction.NONE; // default value is NONE during persisting an entity
}
