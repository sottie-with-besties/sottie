package com.sottie.app.cash.model;

import com.sottie.app.base.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "st_cash")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Cash extends BaseEntity {

    private Long userId;

    private Integer amount;

    private void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Cash setCalculatedAmount(Integer calculatedTotalAmount) {
        this.setAmount(calculatedTotalAmount);
        return this;
    }
}
