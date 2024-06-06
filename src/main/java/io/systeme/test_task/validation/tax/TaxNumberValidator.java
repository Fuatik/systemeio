package io.systeme.test_task.validation.tax;

import io.systeme.test_task.validation.tax.country.TaxNumberValidatorStrategy;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * The TaxNumberValidator class validates tax numbers based on predefined patterns for different countries.
 */
public class TaxNumberValidator implements ConstraintValidator<TaxNumber, String> {

    private final TaxNumberValidatorRegistry registry = new TaxNumberValidatorRegistry();

    @Override
    public void initialize(TaxNumber constraintAnnotation) {}

    @Override
    public boolean isValid(String taxNumber, ConstraintValidatorContext var) {
        if (taxNumber == null) {
            return false;
        }

        String countryCode = taxNumber.substring(0, 2);
        TaxNumberValidatorStrategy validator = registry.getValidator(countryCode);

        if (validator == null || !validator.isValid(taxNumber)) {
            var.disableDefaultConstraintViolation();
            var.buildConstraintViolationWithTemplate("Invalid tax number").addConstraintViolation();
            return false;
        }
        return true;
    }
}
