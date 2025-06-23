package com.sottie.app.gathering.model;

import com.sottie.app.base.domain.BaseEntity;

import com.sottie.app.user.model.Gender;
import com.sottie.app.user.model.User;
import jakarta.persistence.*;
import lombok.*;

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

    private Long host;

    private String title;

    private Long locationId;

    private LocalDateTime gatheringDate;

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

    private Integer ageFrom;

    private Integer ageTo;

    @Enumerated(EnumType.STRING)
    private GenderCategory genderRestriction;

    private Boolean ageRestriction;

    private Boolean mannerRestriction;

    @Transient
    @Setter
    private Boolean friendYn;


    // TODO DB 는 현재 restriction 관련 테이블이 별도로 만들어져 있다
    public void plusPeopleNum(User user) {
        if (user.getGender().equals(Gender.MALE)) {
            this.setCurrentMaleNum(this.getCurrentMaleNum() + 1);
        } else if (user.getGender().equals(Gender.FEMALE)) {
            this.setCurrentFemaleNum(this.getCurrentFemaleNum() + 1);
        }
        this.setCurrentPeopleNum(this.getCurrentPeopleNum() + 1);
    }

    // TODO DB 는 현재 restriction 관련 테이블이 별도로 만들어져 있다
    public void minusPeopleNum(User user) {
        if (user.getGender().equals(Gender.MALE)) {
            this.setCurrentMaleNum(this.getCurrentMaleNum() - 1);
        } else if (user.getGender().equals(Gender.FEMALE)) {
            this.setCurrentFemaleNum(this.getCurrentFemaleNum() - 1);
        }
        this.setCurrentPeopleNum(this.getCurrentPeopleNum() - 1);
    }

    public void friendParticipatedGathering() {
        this.setFriendYn(true);
    }

    public boolean isNoMoreRoom() {
        return this.getPeopleNum().equals(this.getCurrentPeopleNum());
    }

    public boolean isNoRoomForMale() {
        return this.getMaleNum().equals(this.getCurrentMaleNum());
    }

    public boolean isNoRoomForFemale() {
        return this.getFemaleNum().equals(this.getCurrentFemaleNum());
    }

}
