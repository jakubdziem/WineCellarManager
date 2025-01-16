package com.dziem.WineCellarManager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class VintageValidatorImpl implements ConstraintValidator<VintageValidator, Year> {
    @Override
    public boolean isValid(Year year, ConstraintValidatorContext constraintValidatorContext) {
        return year.isAfter(Year.of(0)) && !year.isAfter(Year.now());
    }
}
