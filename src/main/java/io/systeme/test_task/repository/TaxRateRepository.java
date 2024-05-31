package io.systeme.test_task.repository;

import io.systeme.test_task.model.tax.Tax;

public interface TaxRateRepository extends BaseRepository<Tax> {

    Tax findByRegion(String taxRegion);
}
