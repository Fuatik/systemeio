package io.systeme.test_task.validation.tax;

import io.systeme.test_task.validation.tax.country.*;

import java.util.HashMap;
import java.util.Map;

public class TaxNumberValidatorRegistry {
    private final Map<String, TaxNumberValidatorStrategy> validators = new HashMap<>();

    public TaxNumberValidatorRegistry() {
        registerValidator("DE", new GermanyTaxNumberValidator());
        registerValidator("IT", new ItalyTaxNumberValidator());
        registerValidator("GR", new GreeceTaxNumberValidator());
        registerValidator("FR", new FranceTaxNumberValidator());
    }

    public void registerValidator(String countryCode, TaxNumberValidatorStrategy validator) {
        validators.put(countryCode, validator);
    }

    public TaxNumberValidatorStrategy getValidator(String countryCode) {
        return validators.get(countryCode);
    }
}
