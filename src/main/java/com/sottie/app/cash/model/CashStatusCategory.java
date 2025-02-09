package com.sottie.app.cash.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CashStatusCategory {
	PENDING,
	COMPLETED,
	FAILED
}
