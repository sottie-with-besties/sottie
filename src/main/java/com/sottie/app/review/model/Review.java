package com.sottie.app.review.model;

import com.sottie.app.base.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "st_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {

    private Long reviewerId;
    private Long targetUserId;
    private Long gatheringId;
    private Integer rating;
    @Enumerated(EnumType.STRING)
    private ReviewCategory reviewCategory;

    private void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    private void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    private void setGatheringId(Long gatheringId) {
        this.gatheringId = gatheringId;
    }

    private void setRating(Integer rating) {
        this.rating = rating;
    }

    private void setReviewCategory(ReviewCategory reviewCategory) {
        this.reviewCategory = reviewCategory;
    }

    public Review setRatingByReviewCategory(ReviewCategory reviewCategory) {
        this.setReviewCategory(reviewCategory);

        if (reviewCategory.equals(ReviewCategory.LIKE)) {
            this.setRating(1);

        } else if (reviewCategory.equals(ReviewCategory.DISLIKE)) {
            this.setRating(-1);

        } else if (reviewCategory.equals(ReviewCategory.SKIP)) {
            this.setRating(0);

        } else {
            this.setRating(0);
        }

        return this;
    }
}
