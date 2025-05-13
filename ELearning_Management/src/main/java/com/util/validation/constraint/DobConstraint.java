package com.util.validation.constraint;

import com.util.validation.validator.DobValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
    String message() default "Bạn phải lớn hơn 6 tuổi!";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
