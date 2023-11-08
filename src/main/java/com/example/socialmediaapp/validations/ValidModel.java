package com.example.socialmediaapp.validations;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.Payload;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;
@Component
@Constraint(validatedBy = {})
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface ValidModel {
    String message() default "Invalid Values";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends ConstraintValidator<?, ?>> validatedBy();

    Class<?> dtoGroup();
}