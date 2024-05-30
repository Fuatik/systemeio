package io.systeme.test_task.model.tax;

import io.systeme.test_task.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tax_rate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxRate extends BaseEntity {

    @Column(name = "tax_region", nullable = false)
    private String taxRegion;

    @Column(name = "tax_rate", nullable = false)
    private double taxRate;
}

