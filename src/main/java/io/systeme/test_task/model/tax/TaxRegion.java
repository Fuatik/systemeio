package io.systeme.test_task.model.tax;

import lombok.Getter;

@Getter
public enum TaxRegion {

    GERMANY("DE"),
    ITALY("IT"),
    FRANCE("FR"),
    GREECE("GR");

    private final String taxRegionCode;

    TaxRegion(String taxRegionCode) {
        this.taxRegionCode = taxRegionCode;
    }

}
