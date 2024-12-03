package com.sottie.app.gathering.model.record;

import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GenderCategory;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record AddGatheringRequest(
	GatheringCategory gatheringCategory,
	String title,
	Long locationId,

	LocalDateTime gatheringDate,
	String contents,
	Integer peopleNum,
	Integer femaleNum,
	Integer maleNum,
	Integer ageTo,
	Integer ageFrom,
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
