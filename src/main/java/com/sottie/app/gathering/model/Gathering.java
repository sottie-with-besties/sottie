package com.sottie.app.gathering.model;

import com.sottie.app.base.domain.BaseEntity;

import com.sottie.app.user.model.Gender;
import com.sottie.app.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "st_gathering")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Gathering extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private GatheringCategory gatheringCategory;

    private Long hostId;

    private String title;

    private Long locationId;

    @CreationTimestamp
    private LocalDateTime gatheringDate;

    // TODO 아래 필드는 Entity 에는 필요 없음, 검색 파라미터에만 필요
//    private LocalDateTime searchStartDate;
//
//    private LocalDateTime searchEndDate;

    private String contents;

    private Integer peopleNum;

    @Setter
    private Integer currentPeopleNum;

    private Integer femaleNum;

    @Setter
    private Integer currentFemaleNum;

    private Integer maleNum;

    @Setter
    private Integer currentMaleNum;

    private Integer ageTo;

    private Integer ageFrom;

    @Enumerated(EnumType.STRING)
    private GenderCategory genderRestriction;

    private Boolean ageRestriction;

    private Boolean mannerRestriction;

    @OneToMany(mappedBy = "gathering")
    private List<GatheringUser> gatheringUsers;


    // TODO
    // DB 는 현재 restriction 관련 테이블이 별도로 만들어져 있다
    public void plusPeopleNum(User user) {
        if (user.getGender().equals(Gender.MALE)) {
            this.setCurrentMaleNum(this.getCurrentMaleNum() + 1);
        } else if (user.getGender().equals(Gender.FEMALE)) {
            this.setCurrentFemaleNum(this.getCurrentFemaleNum() + 1);
        }
        this.setCurrentPeopleNum(this.getCurrentPeopleNum() + 1);
    }

    // TODO
    // DB 는 현재 restriction 관련 테이블이 별도로 만들어져 있다
    public void minusPeopleNum(User user) {
        if (user.getGender().equals(Gender.MALE)) {
            this.setCurrentMaleNum(this.getCurrentMaleNum() - 1);
        } else if (user.getGender().equals(Gender.FEMALE)) {
            this.setCurrentFemaleNum(this.getCurrentFemaleNum() - 1);
        }
        this.setCurrentPeopleNum(this.getCurrentPeopleNum() - 1);
    }
}
