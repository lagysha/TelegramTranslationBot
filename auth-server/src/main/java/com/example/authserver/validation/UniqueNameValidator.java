package com.example.authserver.validation;

import com.example.authserver.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UniqueNameValidator implements ConstraintValidator<UniqueName, String > {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return userRepository.findUserByUsername(username).isEmpty();
    }
}
