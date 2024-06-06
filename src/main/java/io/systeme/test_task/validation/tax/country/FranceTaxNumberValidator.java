package io.systeme.test_task.validation.tax.country;

import java.util.regex.Pattern;

public class FranceTaxNumberValidator implements TaxNumberValidatorStrategy {
    private static final Pattern FR_PATTERN = Pattern.compile("FR[A-Z]{2}[0-9]{9}");

    @Override
    public boolean isValid(String taxNumber) {
        return FR_PATTERN.matcher(taxNumber).matches();
    }
}
