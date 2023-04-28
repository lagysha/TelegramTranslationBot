package com.example.authserver.controller;

import com.example.authserver.dto.UserDto;
import com.example.authserver.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto) {
        userService.createNewUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User successfully created: " + userDto.getUsername());
    }

    // login endpoint
    @GetMapping("/user")
    public ResponseEntity<UserDto> getAuthenticatedUser(Authentication authentication) {
        var userDto = userService.findByEmail(authentication.getName());
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/user")
    public ResponseEntity<String> changeAuthenticatedUserPassword(
                                                    @RequestParam("oldPassword") String oldPassword,
                                                    @RequestParam("newPassword") String newPassword) {
        userService.changePassword(oldPassword, newPassword);
        return ResponseEntity.ok("User password successfully changed");
    }

    @DeleteMapping("/user/{email}")
    @PreAuthorize("#email == authentication.principal.username OR hasRole(ROLE_ADMIN)")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        userService.deleteAuthenticatedUser(email);
        return ResponseEntity.ok("User was successfully deleted");
    }
}
