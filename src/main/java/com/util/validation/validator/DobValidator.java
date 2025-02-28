package com.util.validation.validator;

import com.util.validation.constraint.DobConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class DobValidator implements ConstraintValidator<DobConstraint, Instant> {

    @Override
    public boolean isValid(Instant instant, ConstraintValidatorContext constraintValidatorContext) {
        if (instant == null)
            return true;
        LocalDate now = LocalDate.now();
        LocalDate input = LocalDate.ofInstant(instant, ZoneOffset.UTC);
        if (input.isAfter(now)) {
            return false;
        }
        return ChronoUnit.YEARS.between(input, now) > 6;
    }

    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
