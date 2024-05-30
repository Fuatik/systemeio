package io.systeme.test_task.model.tax;

import io.systeme.test_task.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tax_rates")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxRate extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private TaxRegion taxRegion;

    private double taxRate;
}
