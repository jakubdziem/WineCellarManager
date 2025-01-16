package com.dziem.WineCellarManager.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = VintageValidatorImpl.class)
@Documented
public @interface VintageValidator {
    String message() default "{message.key}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
