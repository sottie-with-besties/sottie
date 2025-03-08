package com.sottie.app.cash.model.dto;

import com.sottie.app.cash.model.Cash;
import com.sottie.app.cash.model.CashLog;
import com.sottie.app.cash.model.CashStatusCategory;
import com.sottie.app.cash.model.CashTypeCategory;
import com.sottie.app.review.model.Review;
import com.sottie.app.review.model.ReviewCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CashLogDto {

    private Long cashId;

    @Enumerated(EnumType.STRING)
    private CashStatusCategory cashStatus;

    @Enumerated(EnumType.STRING)
    private CashTypeCategory cashType;


    public static CashLogDto from(CashLog cashLog) {
        return new CashLogDto(
                cashLog.getCashId(),
                cashLog.getCashStatus(),
                cashLog.getCashType()
        );
    }
}
