package io.systeme.test_task.validation.tax.country;

import java.util.regex.Pattern;

public class ItalyTaxNumberValidator implements TaxNumberValidatorStrategy {
    private static final Pattern IT_PATTERN = Pattern.compile("IT[0-9]{11}");

    @Override
    public boolean isValid(String taxNumber) {
        return IT_PATTERN.matcher(taxNumber).matches();
    }
}
