package com.sottie.app.review.model.record;

import com.sottie.app.review.model.Review;
import com.sottie.app.review.model.ReviewCategory;
import lombok.Builder;


@Builder
public record CreateReviewRequest(
	Long reviewerId,
	Long targetUserId,
	Long gatheringId,
	ReviewCategory reviewCategory
	) {


	public Review to() {
		Review review = Review.builder()
				.reviewerId(this.reviewerId)
				.targetUserId(this.targetUserId)
				.gatheringId(this.gatheringId)
				.reviewCategory(this.reviewCategory)
				.build();

		return review.setRatingByReviewCategory(this.reviewCategory);
	}
}
