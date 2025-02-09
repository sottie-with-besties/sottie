package com.sottie.app.cash.model;

import com.sottie.app.base.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "st_cash_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CashLog extends BaseEntity {

    private Long userId;

    @Enumerated(EnumType.STRING)
    private CashStatusCategory cashStatus;

    @Enumerated(EnumType.STRING)
    private CashTypeCategory cashType;

}
