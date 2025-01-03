package com.sottie.app.gathering.model.record;

import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GenderCategory;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record CreateGatheringRequest(
	GatheringCategory gatheringCategory,
	String title,
	Long locationId,
	LocalDateTime gatheringDate,
	String contents,
	Integer peopleNum,
	Integer femaleNum,
	Integer maleNum,
	Integer ageFrom,
	Integer ageTo,
	GenderCategory genderRestriction,
	Boolean mannerRestriction,
	Boolean ageRestriction
	) {


	public Gathering to(Long userId) {
		return Gathering.builder()
			.gatheringCategory(this.gatheringCategory)
			.host(userId)
			.title(this.title)
			.locationId(this.locationId)
			.gatheringDate(this.gatheringDate)
			.contents(this.contents)
			.peopleNum(this.peopleNum)
			.currentPeopleNum(0)
			.femaleNum(this.femaleNum)
			.currentFemaleNum(0)
			.maleNum(this.maleNum)
			.currentMaleNum(0)
			.ageFrom(this.ageFrom)
			.ageTo(this.ageTo)
			.genderRestriction(this.genderRestriction)
			.ageRestriction(this.ageRestriction)
			.mannerRestriction(this.mannerRestriction)
			.build();
	}
}
