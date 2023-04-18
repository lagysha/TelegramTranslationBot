package com.example.userservice.data;

import com.example.userservice.validation.UniqueEmail;
import com.example.userservice.validation.UniqueName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter @Setter @ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "username can't be blank")
    @UniqueName
    private String username;

    @UniqueEmail
    private String email;

    @NotBlank(message = "password can't be blank")
    private String password;
}
