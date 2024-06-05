package io.systeme.test_task.validation.coupon;

import io.systeme.test_task.repository.CouponRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The CouponValidator class validates coupon codes based on a predefined pattern.
 */
public class CouponCodeValidator implements ConstraintValidator<CouponCode, String> {

    private static final String COUPON_PATTERN = "[A-Z][0-9]{2}";

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public void initialize(CouponCode constraintAnnotation) {}

    /**
     * Validates the coupon code.
     *
     * @param couponCode The coupon code to validate.
     * @param var        The constraint validator context.
     * @return True if the coupon code is valid, otherwise false.
     */
    @Override
    public boolean isValid(String couponCode, ConstraintValidatorContext var) {
        if (couponCode == null) {
            return true; // Allow null values, use @NotNull for non-null validation
        }
        if (!couponCode.matches(COUPON_PATTERN) || couponRepository.findByCode(couponCode) == null) {
            var.disableDefaultConstraintViolation();
            var.buildConstraintViolationWithTemplate("Invalid coupon code").addConstraintViolation();
            return false;
        }
        return true;
    }
}
