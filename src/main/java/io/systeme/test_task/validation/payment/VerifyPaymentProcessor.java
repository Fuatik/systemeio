package io.systeme.test_task.validation.payment;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = VerifyPaymentProcessorValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyPaymentProcessor {
    String message() default "Invalid payment processor";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
