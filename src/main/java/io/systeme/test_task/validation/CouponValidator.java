package io.systeme.test_task.validation;

import io.systeme.test_task.exception.BadRequestException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * The CouponValidator class validates coupon codes based on a predefined pattern.
 */
public class CouponValidator implements ConstraintValidator<Coupon, String> {

    private static final String COUPON_PATTERN = "[A-Z][0-9]{2}";

    @Override
    public void initialize(Coupon constraintAnnotation) {}

    /**
     * Validates the coupon code.
     *
     * @param couponCode The coupon code to validate.
     * @param var        The constraint validator context.
     * @return True if the coupon code is valid, otherwise false.
     * @throws BadRequestException if the coupon code is invalid.
     */
    @Override
    public boolean isValid(String couponCode, ConstraintValidatorContext var) {
        if (couponCode == null) {
            return true; // Allow null values, use @NotNull for non-null validation
        }
        if (!couponCode.matches(COUPON_PATTERN)) {
            var.disableDefaultConstraintViolation();
            var.buildConstraintViolationWithTemplate("Invalid coupon code").addConstraintViolation();
            return false;
        }
        return true;
    }
}
