package com.sottie.app.review.application;

import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.gathering.repository.GatheringUserRepository;
import com.sottie.app.review.error.ReviewErrorCode;
import com.sottie.app.review.model.Review;
import com.sottie.app.review.model.dto.ReviewDto;
import com.sottie.app.review.model.record.CreateReviewRequest;
import com.sottie.app.review.repository.ReviewRepository;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import com.sottie.utils.SottieUserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreateReviewService {

	private final GatheringRepository gatheringRepository;
	private final UserRepository userRepository;
	private final GatheringUserRepository gatheringUserRepository;

	private final ReviewRepository reviewRepository;

	private static final long REVIEW_TIME_AVAILABLE = 24;

	// TODO 매너온도에 리뷰 점수가 반영되는 로직 구현 필요
	public List<ReviewDto> createReviews(List<CreateReviewRequest> createReviewRequests) {

		Long userId = SottieUserUtils.getUserIdLong();

		Optional<User> optUser = userRepository.findById(userId);

		if (optUser.isPresent()) {
			User user = optUser.get();

			List<ReviewDto> reviewDtos = new ArrayList<>();

			for (CreateReviewRequest createReviewRequest : createReviewRequests) {
				Review review = createReviewRequest.to();

				checkCreateReviewValidation(review, user);

				Review savedReview = reviewRepository.save(review);

				reviewDtos.add(ReviewDto.from(savedReview));
			}

			return reviewDtos;

		} else {
			throw CommonException.builder(GatheringErrorCode.NOT_HOST_USER).build();
		}
	}

	private void checkCreateReviewValidation(Review review, User user) {

		// 리뷰어가 모집글 멤버인지 확인
		checkReviewerIsGatheringMember(review, user);

		// 리뷰 타겟유저가 모집글 멤버인지 확인
		checkTargetUserIsGatheringMember(review);

		// 리뷰 시간이 모집글 모집시간 24시간 이후인지 확인
		checkReviewTime(review);

	}

	private void checkReviewerIsGatheringMember(Review review, User user) {
		if (!review.getReviewerId().equals(user.getId())) {
			log.error(ReviewErrorCode.INVALID_REVIEWER.getMessage());
			throw CommonException.builder(ReviewErrorCode.INVALID_REVIEWER).build();
		}

		Optional<GatheringUser> gatheringUser = gatheringUserRepository.findByGatheringIdAndUserId(review.getGatheringId(), review.getReviewerId());
		if (gatheringUser.isEmpty()) {
			log.error(ReviewErrorCode.INVALID_REVIEWER.getMessage());
			throw CommonException.builder(ReviewErrorCode.INVALID_REVIEWER).build();
		}
	}

	private void checkTargetUserIsGatheringMember(Review review) {
		Optional<GatheringUser> gatheringUser = gatheringUserRepository.findByGatheringIdAndUserId(review.getGatheringId(), review.getTargetUserId());
		if (gatheringUser.isEmpty()) {
			log.error(ReviewErrorCode.INVALID_TARGET_USER.getMessage());
			throw CommonException.builder(ReviewErrorCode.INVALID_TARGET_USER).build();
		}
	}

	private void checkReviewTime(Review review) {

		Optional<Gathering> gatheringOpt = gatheringRepository.findById(review.getGatheringId());

		if (gatheringOpt.isPresent()) {
			Duration between = Duration.between(gatheringOpt.get().getGatheringDate(), LocalDateTime.now());

			if (between.toHours() < REVIEW_TIME_AVAILABLE) {
				log.error(ReviewErrorCode.NOT_REVIEW_TIME.getMessage());
				throw CommonException.builder(ReviewErrorCode.NOT_REVIEW_TIME).build();
			}
		} else {
			log.error(ReviewErrorCode.REVIEW_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(ReviewErrorCode.REVIEW_INSUFFICIENT_INFORMATION).build();
		}
	}
}
