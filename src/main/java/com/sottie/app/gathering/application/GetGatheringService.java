package com.sottie.app.gathering.application;

import com.sottie.app.gathering.model.record.DefaultGatheringRequest;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GenderCategory;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.gathering.specification.GatheringSpecification;
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

	public List<Gathering> getGatherings(DefaultGatheringRequest defaultGatheringRequest) {

		if (defaultGatheringRequest == null) {
			return gatheringRepository.findAll();

		} else {

			GatheringCategory gatheringCategory = defaultGatheringRequest.gatheringCategory();
			String title = defaultGatheringRequest.title();
			Long locationId = defaultGatheringRequest.locationId();
			Integer peopleNum = defaultGatheringRequest.peopleNum();
			GenderCategory genderRestriction = defaultGatheringRequest.genderRestriction();
			Boolean mannerRestrictionYn = defaultGatheringRequest.mannerRestriction();
			Boolean ageRestrictionYn = defaultGatheringRequest.ageRestriction();

			Specification<Gathering> spec = Specification.where(GatheringSpecification.equalGatheringCategory(gatheringCategory));
			spec = spec.or(GatheringSpecification.likeTitle(title)
					.or(GatheringSpecification.equalLocationId(locationId))
					.or(GatheringSpecification.equalPeopleNum(peopleNum))
					.or(GatheringSpecification.restrictGender(genderRestriction))
					.or(GatheringSpecification.restrictManner(mannerRestrictionYn))
					.or(GatheringSpecification.restrictAge(ageRestrictionYn))
			);
			return gatheringRepository.findAll(spec);
		}


	}
}
