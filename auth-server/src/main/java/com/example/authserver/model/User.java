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

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private List<Role> roles;

    /**
        @param role must come without any prefix (e.g. "ROLE_")
        @return boolean value: whether user has this role or not
     */
    public boolean hasRole(String role) {
        return roles.stream()
            .map (Role::getName)
            .map(roleName -> roleName.replace("ROLE_", ""))
            .anyMatch(clearRoleName -> clearRoleName.equals(role));
    }

}
