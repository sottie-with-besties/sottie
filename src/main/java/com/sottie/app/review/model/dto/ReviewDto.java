package com.sottie.app.review.model.dto;

import com.sottie.app.review.model.Review;
import com.sottie.app.review.model.ReviewCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReviewDto {

    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Long registeredBy = 0L;

    private Long modifiedBy = 0L;

    private Long reviewerId;

    private Long targetUserId;

    private Long gatheringId;

    private Integer rating;

    private ReviewCategory reviewCategory;


    public static ReviewDto from(Review review) {
        return new ReviewDto(
            review.getId(),
                review.getCreatedDate(),
                review.getModifiedDate(),
                review.getRegisteredBy(),
                review.getModifiedBy(),
                review.getReviewerId(),
                review.getTargetUserId(),
                review.getGatheringId(),
                review.getRating(),
                review.getReviewCategory()
        );
    }
}
