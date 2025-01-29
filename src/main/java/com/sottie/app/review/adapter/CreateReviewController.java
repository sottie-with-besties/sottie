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

@RestController
@RequiredArgsConstructor
class CreateReviewController {

    private final CreateReviewService createReviewService;

    @PostMapping("/sottie/review")
    public ResponseEntity<ReviewDto> createReview(@RequestBody @Valid CreateReviewRequest createReviewRequest) {
        ReviewDto result = createReviewService.createReview(createReviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
