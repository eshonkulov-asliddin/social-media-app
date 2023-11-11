package com.example.socialmediaapp.validations;

import com.example.socialmediaapp.entities.Post;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PostValidator implements ConstraintValidator<ValidModel, Post> {
    // Post specific validation rules go here
    @Override
    public void initialize(ValidModel constraintAnnotation) {}

    @Override
    public boolean isValid(Post post, ConstraintValidatorContext constraintValidatorContext) {
        // Add your post-specific validation logic here
        if (post == null) return false;
        return !StringUtils.isEmpty(post.getBody());
    }
}
