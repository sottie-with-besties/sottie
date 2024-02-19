package com.sottie.app.gathering.application;

import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GenderCategory;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.gathering.specification.GatheringSpecification;
import com.sottie.app.user.model.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetGatheringService {

	private final GatheringRepository gatheringRepository;

	public List<Gathering> getGatherings(
			GatheringCategory gatheringCategory,
			String title,
			Long locationId,
			Integer peopleNum,
			GenderCategory genderRestriction,
			Boolean mannerRestrictionYn,
			Boolean ageRestrictionYn) {

		Specification<Gathering> spec = Specification.where(GatheringSpecification.equalGatheringCategory(gatheringCategory));
		spec = spec.and(GatheringSpecification.likeTitle(title)
				.and(GatheringSpecification.equalLocationId(locationId))
				.and(GatheringSpecification.equalPeopleNum(peopleNum))
				.and(GatheringSpecification.restrictGender(genderRestriction))
				.and(GatheringSpecification.restrictManner(mannerRestrictionYn))
				.and(GatheringSpecification.restrictAge(ageRestrictionYn))
		);

		return gatheringRepository.findAll(spec);
	}
}
