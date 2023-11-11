package com.example.socialmediaapp.validations;

import com.example.socialmediaapp.entities.User;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements ConstraintValidator<ValidModel, User> {
    // User-specific validation rules go here
    @Override
    public void initialize(ValidModel constraintAnnotation) {}

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user == null) {
            return false;
        }
        // Perform your validation logic here
        // For example, let's make sure none of the fields are null or empty
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())
                || StringUtils.isBlank(user.getEmail())) {
            return false;
        }
        return true;
    }
}