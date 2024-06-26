package io.systeme.test_task.validation.product;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The VerifyProduct annotation is used to validate coupon codes.
 */
@Constraint(validatedBy = VerifyProductValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyProduct {
    String message() default "Product not found";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
