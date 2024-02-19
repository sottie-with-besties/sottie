package com.sottie.app.gathering.model;

import com.sottie.app.base.domain.BaseEntity;

import com.sottie.app.user.model.Gender;
import com.sottie.app.user.model.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @JoinColumn(name = "user_id")
    private User host;

    private String title;

    private Long locationId;

    private String contents;

    private Integer peopleNum;

    private Integer femaleNum;

    private Integer maleNum;

    private Integer joinPeopleNum;

    private Integer joinFemaleNum;

    private Integer joinMaleNum;

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
    public Gathering plusPeopleNum(User user) {
        if (user.getGender().equals(Gender.MALE)) {
            this.builder().maleNum(this.getMaleNum() + 1).build();
        } else if (user.getGender().equals(Gender.FEMALE)) {
            this.builder().maleNum(this.getFemaleNum() + 1).build();
        }
        this.builder().peopleNum(this.getPeopleNum() + 1).build();

        return this;
    }

    // TODO
    // DB 는 현재 restriction 관련 테이블이 별도로 만들어져 있다
    public Gathering minusPeopleNum(User user) {
        if (user.getGender().equals(Gender.MALE)) {
            this.builder().maleNum(this.getMaleNum() - 1).build();
        } else if (user.getGender().equals(Gender.FEMALE)) {
            this.builder().maleNum(this.getFemaleNum() - 1).build();
        }
        this.builder().peopleNum(this.getPeopleNum() - 1).build();

        return this;
    }
}
