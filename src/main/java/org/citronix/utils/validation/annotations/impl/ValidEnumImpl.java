package org.citronix.utils.validation.annotations.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.citronix.utils.validation.annotations.ValidEnum;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidEnumImpl implements ConstraintValidator<ValidEnum, String> {
    private Set<String> acceptedValues;
    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        acceptedValues = Arrays.stream(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("here" + acceptedValues);
        return s == null || acceptedValues.contains(s.toUpperCase());
    }
}
