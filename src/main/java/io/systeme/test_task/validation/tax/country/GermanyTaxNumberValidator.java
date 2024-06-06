package io.systeme.test_task.validation.tax.country;

import java.util.regex.Pattern;

public class GermanyTaxNumberValidator implements TaxNumberValidatorStrategy {
    private static final Pattern DE_PATTERN = Pattern.compile("DE[0-9]{9}");

    @Override
    public boolean isValid(String taxNumber) {
        return DE_PATTERN.matcher(taxNumber).matches();
    }
}
