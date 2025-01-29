package com.sottie.app.review.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewCategory {
	LIKE,
	DISLIKE,
	SKIP
}
