package io.systeme.test_task.validation;

import io.systeme.test_task.exception.InvalidCouponCodeException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CouponValidator implements ConstraintValidator<Coupon, String> {

    private static final String COUPON_PATTERN = "[A-Z][0-9]{2}";

    @Override
    public void initialize(Coupon constraintAnnotation) {
        // This method can be used to initialize the validator in any way, but it's not necessary here.
    }

    @Override
    public boolean isValid(String couponCode, ConstraintValidatorContext var) {
        if (couponCode == null) {
            return true; // Allow null values, use @NotNull for non-null validation
        }
        if (!couponCode.matches(COUPON_PATTERN)) {
            var.disableDefaultConstraintViolation();
            var.buildConstraintViolationWithTemplate("Invalid coupon code").addConstraintViolation();
            throw new InvalidCouponCodeException("Invalid coupon code");
        }
        return true;
    }
}
