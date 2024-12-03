package com.sottie.app.gathering.application;

import com.sottie.app.gathering.model.record.DefaultGatheringRequest;
import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GenderCategory;
import com.sottie.app.gathering.model.record.GetGatheringRequest;
import com.sottie.app.gathering.repository.GatheringRepository;
import com.sottie.app.gathering.specification.GatheringSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetGatheringService {

	private final GatheringRepository gatheringRepository;

	public List<Gathering> getGatherings(GetGatheringRequest getGatheringRequest) {

		if (getGatheringRequest == null) {
			return gatheringRepository.findAll();

		} else {

			GatheringCategory gatheringCategory = getGatheringRequest.gatheringCategory();
			String title = getGatheringRequest.title();
			Long locationId = getGatheringRequest.locationId();
			LocalDateTime searchStartTime = getGatheringRequest.searchStartDate();
			LocalDateTime searchEndTime = getGatheringRequest.searchEndDate();
			Integer peopleNum = getGatheringRequest.peopleNum();
			GenderCategory genderRestriction = getGatheringRequest.genderRestriction();
			Boolean mannerRestrictionYn = getGatheringRequest.mannerRestriction();
			Boolean ageRestrictionYn = getGatheringRequest.ageRestriction();

			Specification<Gathering> spec = Specification.where(GatheringSpecification.equalGatheringCategory(gatheringCategory));
			spec = spec.or(GatheringSpecification.likeTitle(title)
					.or(GatheringSpecification.equalLocationId(locationId))
					.or(GatheringSpecification.equalPeopleNum(peopleNum))
					.or(GatheringSpecification.restrictGender(genderRestriction))
					.or(GatheringSpecification.restrictManner(mannerRestrictionYn))
					.or(GatheringSpecification.restrictAge(ageRestrictionYn))
					.or(GatheringSpecification.betweenGatheringDate(searchStartTime, searchEndTime))
			);
			return gatheringRepository.findAll(spec);
		}


	}
}
