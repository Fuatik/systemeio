package io.systeme.test_task.validation.coupon;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Coupon annotation is used to validate coupon codes.
 */
@Constraint(validatedBy = CouponCodeValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CouponCode {
    String message() default "Invalid coupon code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
