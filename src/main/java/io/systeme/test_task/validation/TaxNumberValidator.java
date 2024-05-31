package io.systeme.test_task.validation;

import io.systeme.test_task.exception.InvalidTaxNumberException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class TaxNumberValidator implements ConstraintValidator<TaxNumber, String> {

    private static final Pattern DE_PATTERN = Pattern.compile("DE[0-9]{9}");
    private static final Pattern IT_PATTERN = Pattern.compile("IT[0-9]{11}");
    private static final Pattern GR_PATTERN = Pattern.compile("GR[0-9]{9}");
    private static final Pattern FR_PATTERN = Pattern.compile("FR[A-Z]{2}[0-9]{9}");


    @Override
    public void initialize(TaxNumber constraintAnnotation) {
        // This method can be used to initialize the validator in any way, but it's not necessary here.
    }

    @Override
    public boolean isValid(String taxNumber, ConstraintValidatorContext var) {
        if (taxNumber == null) {
            return false; // Allow null values, use @NotNull for non-null validation
        }
        if (!DE_PATTERN.matcher(taxNumber).matches() &&
                !IT_PATTERN.matcher(taxNumber).matches() &&
                !GR_PATTERN.matcher(taxNumber).matches() &&
                !FR_PATTERN.matcher(taxNumber).matches()) {
            var.disableDefaultConstraintViolation();
            var.buildConstraintViolationWithTemplate("Invalid tax number").addConstraintViolation();
            throw new InvalidTaxNumberException("Invalid tax number");
        }
        return true;
    }
}
