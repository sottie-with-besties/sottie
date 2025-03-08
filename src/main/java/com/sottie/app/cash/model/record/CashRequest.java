package com.sottie.app.cash.model.record;

import com.sottie.app.cash.model.Cash;
import lombok.Builder;


@Builder
public record CashRequest(
		Long userId,

		Integer amount
	) {


	public Cash to() {
		Cash cash = Cash.builder()
				.userId(this.userId)
				.amount(this.amount)
				.build();

		return cash;
	}
}
