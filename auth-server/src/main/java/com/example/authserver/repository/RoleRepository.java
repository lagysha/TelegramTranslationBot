package com.example.authserver.repository;

import com.example.authserver.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.isDefault = true")
    List<Role> getDefaultRoles();
}
