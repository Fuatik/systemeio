package io.systeme.test_task.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The TaxNumber annotation is used to validate tax numbers.
 */
@Constraint(validatedBy = TaxNumberValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface TaxNumber {
    String message() default "Invalid tax number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
