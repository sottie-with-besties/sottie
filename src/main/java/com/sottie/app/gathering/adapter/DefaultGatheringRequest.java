package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GenderCategory;
import lombok.Builder;

@Builder
record DefaultGatheringRequest(
	GatheringCategory gatheringCategory,
	String title,
	Long locationId,
	String contents,
	Integer peopleNum,
	Integer femaleNum,
	Integer maleNum,
	Integer ageTo,
	Integer ageFrom,
	Integer mannerTemperature,
	GenderCategory genderRestriction,
	Boolean mannerRestrictionYn,
	Boolean ageRestrictionYn
	) {

	Gathering to() {
		return Gathering.builder()
			.gatheringCategory(this.gatheringCategory)
			.title(this.title)
			.locationId(this.locationId)
			.contents(this.contents)
			.peopleNum(this.peopleNum)
			.femaleNum(this.femaleNum)
			.maleNum(this.maleNum)
			.ageTo(this.ageTo)
			.ageFrom(this.ageFrom)
			.mannerTemperature(this.mannerTemperature)
			.genderRestriction(this.genderRestriction)
			.ageRestrictionYn(this.ageRestrictionYn)
			.mannerRestrictionYn(this.mannerRestrictionYn)
			.build();
	}
}
