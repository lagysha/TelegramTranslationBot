package com.example.authserver.service;

import com.example.authserver.dto.UserDto;
import com.example.authserver.mapper.UserMapper;
import com.example.authserver.model.Role;
import com.example.authserver.repository.RoleRepository;
import com.example.authserver.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsManagerImpl implements UserDetailsManager {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final SecurityContextHolderStrategy securityContextHolderStrategy;

    public UserDetailsManagerImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                  UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    }

    @Override
    public void deleteUser(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = this.securityContextHolderStrategy.getContext().getAuthentication();
        String userPassword = currentUser.getCredentials().toString();

        if (passwordEncoder.matches(oldPassword, newPassword)) {
            var user = userRepository.findByEmail(currentUser.getName()).orElseThrow();
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        else {
            throw new IllegalArgumentException("old password and the current one don't match");
        }
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(
            () -> new UsernameNotFoundException("User with that email doesn't exist: " + email)
        );
        return new User(user.getEmail(), user.getPassword(), user.getRoles());
    }

    @Override
    public void createUser(UserDetails userDto) {
        var user = userMapper.userDtotoUser((UserDto) userDto);

        // encode password and set default roles
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(getDefaultUserRoles());

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public void updateUser(UserDetails user) {
    }

    private List<Role> getDefaultUserRoles() {
        return roleRepository.getDefaultRoles();
    }
}
