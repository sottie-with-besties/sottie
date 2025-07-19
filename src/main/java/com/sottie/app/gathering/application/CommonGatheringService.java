package com.sottie.app.gathering.application;

import com.sottie.app.gathering.error.GatheringErrorCode;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GenderCategory;
import com.sottie.app.user.model.Gender;
import com.sottie.app.user.model.User;
import com.sottie.errors.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CommonGatheringService {

	public static void checkNotNullValidation(Gathering gathering) {
		if (gathering.getTitle().isEmpty()) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getLocationId() == null || gathering.getLocationId().equals(0L)) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getPeopleNum() == null || gathering.getPeopleNum().equals(0)) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();

		} else if (gathering.getGatheringDate() == null) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
		}
	}

	public static void checkGenderRestriction(Gathering gathering) {

		if (Objects.isNull(gathering.getGatheringCategory())) {
			log.error(GatheringErrorCode.INVALID_GENDER_RESTRICTION.getMessage());
			throw CommonException.builder(GatheringErrorCode.INVALID_GENDER_RESTRICTION).build();
		}

		if (gathering.getGenderRestriction().equals(GenderCategory.MIX)) {
			if (gathering.getMaleNum().equals(0) ||  gathering.getFemaleNum().equals(0)) {
				log.error(GatheringErrorCode.INVALID_GENDER_RESTRICTION.getMessage());
				throw CommonException.builder(GatheringErrorCode.INVALID_GENDER_RESTRICTION).build();
			}

		} else if (gathering.getGenderRestriction().equals(GenderCategory.MALE)) {
			if ((!Objects.equals(gathering.getMaleNum(), gathering.getPeopleNum()))) {
				log.error(GatheringErrorCode.INVALID_GENDER_RESTRICTION.getMessage());
				throw CommonException.builder(GatheringErrorCode.INVALID_GENDER_RESTRICTION).build();
			}

		} else if (gathering.getGenderRestriction().equals(GenderCategory.FEMALE)) {
			if ((!Objects.equals(gathering.getFemaleNum(), gathering.getPeopleNum()))) {
				log.error(GatheringErrorCode.INVALID_GENDER_RESTRICTION.getMessage());
				throw CommonException.builder(GatheringErrorCode.INVALID_GENDER_RESTRICTION).build();
			}

		} else if (gathering.getGenderRestriction().equals(GenderCategory.NONE)) {
			if (!(gathering.getMaleNum().equals(0) && gathering.getFemaleNum().equals(0))) {
				log.error(GatheringErrorCode.INVALID_GENDER_RESTRICTION.getMessage());
				throw CommonException.builder(GatheringErrorCode.INVALID_GENDER_RESTRICTION).build();
			}
		}
	}

	public static void checkGatheringDate(Gathering gathering) {
		final int GATHERING_TIME_LIMIT = 168;

		Duration between = Duration.between(gathering.getGatheringDate(), LocalDateTime.now());

		if (between.toHours() > GATHERING_TIME_LIMIT) {
			log.error(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION.getMessage());
			throw CommonException.builder(GatheringErrorCode.GATHERING_INSUFFICIENT_INFORMATION).build();
		}
	}

	public static void checkGatheringAgeRestriction(Gathering gathering) {

		if (gathering.getAgeRestriction().equals(Boolean.TRUE)) {
			if (gathering.getAgeTo().equals(0) || gathering.getAgeFrom().equals(0)) {
				log.error(GatheringErrorCode.INVALID_AGE_RESTRICTION.getMessage());
				throw CommonException.builder(GatheringErrorCode.INVALID_AGE_RESTRICTION).build();
			}

		} else if (gathering.getAgeRestriction().equals(Boolean.FALSE)) {
			// 나이제한 없다면 AgeTo, AgeFrom 둘 다 '0'
			if (!gathering.getAgeTo().equals(0) || !gathering.getAgeFrom().equals(0)) {
				log.error(GatheringErrorCode.INVALID_AGE_RESTRICTION.getMessage());
				throw CommonException.builder(GatheringErrorCode.INVALID_AGE_RESTRICTION).build();
			}
		}
	}

	/**
	 * Invite 로직에서 초대 응답 값이 APPROVED 일 때, 이미 채팅방 활성화 된 방인지 or 남/녀 인원 빈자리 있는지 확인 필요
	 */
	public static Boolean isNoMoreRoomGathering(Gathering gathering, User user) {

		GenderCategory genderCategory = gathering.getGenderRestriction();
		Gender gender = user.getGender();

		if (gathering.isNoMoreRoom()) {
			return true;
		}

		switch (genderCategory) {
			case NONE:
				return gathering.isNoMoreRoom();
			case MIX:
				if (gender.equals(Gender.MALE)) {
					return gathering.isNoRoomForMale();
				} else {
					return gathering.isNoRoomForFemale();
				}
			case FEMALE:
				if (gender.equals(Gender.MALE)) {
					return true;
				} else {
					return gathering.isNoRoomForFemale();
				}
			case MALE:
				if (gender.equals(Gender.FEMALE)) {
					return true;
				} else {
					return gathering.isNoRoomForMale();
				}
			default:
				return true;
		}
	}

	public static int calculateAge(String birthday) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		LocalDate birthDate = LocalDate.parse(birthday, formatter);
		LocalDate today = LocalDate.now();

		return Period.between(birthDate, today).getYears();
	}

	public static boolean isAgeOutsideRange(int age, int ageFrom, int ageTo) {
		return age < ageFrom || age > ageTo;
	}
}
