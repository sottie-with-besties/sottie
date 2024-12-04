package com.sottie.app.gathering.model.record;

import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GenderCategory;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record DefaultGatheringRequest(
	Long id,
	GatheringCategory gatheringCategory,
	String title,
	Long locationId,

	LocalDateTime gatheringDate,

	LocalDateTime searchStartDate,

	LocalDateTime searchEndDate,
	String contents,
	Integer peopleNum,

	Integer currentPeopleNum,
	Integer femaleNum,

	Integer currentFemaleNum,
	Integer maleNum,

	Integer currentMaleNum,
	Integer ageTo,
	Integer ageFrom,
	GenderCategory genderRestriction,
	Boolean mannerRestriction,
	Boolean ageRestriction
	) {


	/**
	 * 모집글 생성시 사용
	 * @param userId
	 */
	public Gathering to(Long userId) {
		return Gathering.builder()
			.gatheringCategory(this.gatheringCategory)
			.host(userId)
			.title(this.title)
			.locationId(this.locationId)
			.contents(this.contents)
			.peopleNum(this.peopleNum)
			.femaleNum(this.femaleNum)
			.maleNum(this.maleNum)
			.ageTo(this.ageTo)
			.ageFrom(this.ageFrom)
			.genderRestriction(this.genderRestriction)
			.ageRestriction(this.ageRestriction)
			.mannerRestriction(this.mannerRestriction)
			.build();
	}
}
