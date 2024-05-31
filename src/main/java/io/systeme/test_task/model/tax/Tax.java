package io.systeme.test_task.model.tax;

import io.systeme.test_task.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tax")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tax extends BaseEntity {

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "rate", nullable = false)
    private double rate;
}

