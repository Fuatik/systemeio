package io.systeme.test_task.repository;

import io.systeme.test_task.model.tax.TaxRate;

public interface TaxRateRepository extends BaseRepository<TaxRate> {

    TaxRate findByTaxRegion(String taxRegion);
}
