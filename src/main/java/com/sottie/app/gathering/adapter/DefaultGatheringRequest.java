package com.sottie.app.gathering.adapter;

import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GenderCategory;
import com.sottie.app.user.model.User;
import lombok.Builder;

@Builder
public record DefaultGatheringRequest(
	Long id,
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
			.joinPeopleNum(0)
			.joinFemaleNum(0)
			.joinMaleNum(0)
			.ageTo(this.ageTo)
			.ageFrom(this.ageFrom)
			.mannerTemperature(this.mannerTemperature)
			.genderRestriction(this.genderRestriction)
			.ageRestrictionYn(this.ageRestrictionYn)
			.mannerRestrictionYn(this.mannerRestrictionYn)
			.build();
	}
}
