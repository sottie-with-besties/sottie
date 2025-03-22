package com.sottie.app.gathering.model.dto;

import com.sottie.app.gathering.model.Gathering;
import com.sottie.app.gathering.model.GatheringCategory;
import com.sottie.app.gathering.model.GatheringUser;
import com.sottie.app.gathering.model.GenderCategory;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
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

    // 모집인원이 충족되어 모집글이 리스트상에서 숨김처리 되었는지 여부
    // 모집인원 충족시 true
    // 채팅방에서 인원 이탈이 생길경우 다시 false 로 변경
    private Boolean isHide;

    public static boolean isHide(Gathering gathering) {
        return gathering.getPeopleNum().equals(gathering.getCurrentPeopleNum());
    }

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
            isHide(gathering)
        );
    }
}
