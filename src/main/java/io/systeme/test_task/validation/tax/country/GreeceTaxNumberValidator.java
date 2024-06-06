package io.systeme.test_task.validation.tax.country;

import java.util.regex.Pattern;

public class GreeceTaxNumberValidator implements TaxNumberValidatorStrategy {
    private static final Pattern GR_PATTERN = Pattern.compile("GR[0-9]{9}");

    @Override
    public boolean isValid(String taxNumber) {
        return GR_PATTERN.matcher(taxNumber).matches();
    }
}
