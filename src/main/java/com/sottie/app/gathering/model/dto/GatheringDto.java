package com.sottie.app.gathering.model.dto;

import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.gathering.model.GenderCategory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class GatheringDto {

    private Long id;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    private Long registeredBy = 0L;

    private Long modifiedBy = 0L;

    private GatheringCategory gatheringCategory;

    private Long host;

    private String title;

    private Long locationId;

    private LocalDateTime gatheringDate;

    private String contents;

    private Integer peopleNum;

    private Integer currentPeopleNum;

    private Integer femaleNum;

    private Integer currentFemaleNum;

    private Integer maleNum;

    private Integer currentMaleNum;

    private Integer ageFrom;

    private Integer ageTo;

    private GenderCategory genderRestriction;

    private Boolean ageRestriction;

    private Boolean mannerRestriction;

    private List<GatheringUser> gatheringUsers;

    public static GatheringDto from(Gathering gathering) {
        return new GatheringDto(
            gathering.getId(),
            gathering.getCreatedDate(),
            gathering.getModifiedDate(),
            gathering.getRegisteredBy(),
            gathering.getModifiedBy(),
            gathering.getGatheringCategory(),
            gathering.getHost(),
            gathering.getTitle(),
            gathering.getLocationId(),
            gathering.getGatheringDate(),
            gathering.getContents(),
            gathering.getPeopleNum(),
            gathering.getCurrentPeopleNum(),
            gathering.getFemaleNum(),
            gathering.getCurrentFemaleNum(),
            gathering.getMaleNum(),
            gathering.getCurrentMaleNum(),
            gathering.getAgeFrom(),
            gathering.getAgeTo(),
            gathering.getGenderRestriction(),
            gathering.getAgeRestriction(),
            gathering.getMannerRestriction(),
            gathering.getGatheringUsers()
        );
    }
}
