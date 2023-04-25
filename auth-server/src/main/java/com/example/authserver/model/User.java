package com.example.authserver.model;

import com.example.authserver.validation.UniqueEmail;
import com.example.authserver.validation.UniqueName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="users")
@Getter @Setter @ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "username can't be blank")
    @UniqueName
    private String username;

    @UniqueEmail
    @NotBlank(message = "email can't be blank")
    private String email;

    private String password;

    private boolean emailVerified;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private List<Role> roles;

}
