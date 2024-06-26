package io.systeme.test_task.model.product;

import io.systeme.test_task.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coupon")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coupon extends BaseEntity {

    @Column(name = "code", nullable = false)
    @Pattern(regexp = "[A-Z][0-9]{2}", message = "Strict coupon format: uppercase letter and two digits")
    private String code;

    @Column(name = "discount", nullable = false)
    private double discount;

    @Column(name = "is_percentage", nullable = false)
    private boolean isPercentage;
}
