package com.sottie.app.review.adapter;

import com.sottie.app.review.application.CreateReviewService;
import com.sottie.app.review.model.dto.ReviewDto;
import com.sottie.app.review.model.record.CreateReviewRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class CreateReviewController {

    private final CreateReviewService createReviewService;

    @PostMapping("/sottie/reviews")
    public ResponseEntity<List<ReviewDto>> createReview(@RequestBody @Valid List<CreateReviewRequest> createReviewRequests) {
        List<ReviewDto> reviews = createReviewService.createReviews(createReviewRequests);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviews);
    }

}
