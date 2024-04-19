package com.sottie.app.gathering.model;

import com.sottie.app.base.domain.BaseEntity;

import com.sottie.app.user.model.Gender;
import com.sottie.app.user.model.User;
import jakarta.persistence.*;
import lombok.*;

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

    private String contents;

    private Integer peopleNum;

    private Integer femaleNum;

    private Integer maleNum;

    @Setter
    private Integer joinPeopleNum = 0;

    @Setter
    private Integer joinFemaleNum = 0;

    @Setter
    private Integer joinMaleNum = 0;

    private Integer mannerTemperature;

    private Integer ageTo;

    private Integer ageFrom;

    @Enumerated(EnumType.STRING)
    private GenderCategory genderRestriction;

    private Boolean ageRestrictionYn;

    private Boolean mannerRestrictionYn;

    @OneToMany(mappedBy = "gathering")
    private List<GatheringUser> gatheringUsers;


    // TODO
    // DB 는 현재 restriction 관련 테이블이 별도로 만들어져 있다
    public void plusPeopleNum(User user) {
        if (user.getGender().equals(Gender.MALE)) {
            this.setJoinMaleNum(this.getJoinMaleNum() + 1);
        } else if (user.getGender().equals(Gender.FEMALE)) {
            this.setJoinFemaleNum(this.getJoinFemaleNum() + 1);
        }
        this.setJoinPeopleNum(this.getJoinPeopleNum() + 1);
    }

    // TODO
    // DB 는 현재 restriction 관련 테이블이 별도로 만들어져 있다
    public void minusPeopleNum(User user) {
        if (user.getGender().equals(Gender.MALE)) {
            this.setJoinMaleNum(this.getJoinMaleNum() - 1);
        } else if (user.getGender().equals(Gender.FEMALE)) {
            this.setJoinFemaleNum(this.getJoinFemaleNum() - 1);
        }
        this.setJoinPeopleNum(this.getJoinPeopleNum() - 1);
    }
}
