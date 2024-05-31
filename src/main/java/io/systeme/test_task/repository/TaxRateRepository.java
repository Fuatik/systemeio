package io.systeme.test_task.repository;

import io.systeme.test_task.model.tax.Tax;
import org.springframework.data.jpa.repository.Query;

public interface TaxRateRepository extends BaseRepository<Tax> {

    @Query("SELECT t FROM Tax t WHERE t.region = :region")
    Tax findByRegion(String region);
}
