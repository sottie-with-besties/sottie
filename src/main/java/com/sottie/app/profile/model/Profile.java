package com.sottie.app.profile.model;

import com.sottie.app.base.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "st_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Profile extends BaseEntity {

	private Long userId;

	@Column(length = 200)
	private String photo;

	@Column(length = 15)
	private String moodStatus;

	@Column(length = 100)
	private String introPhrase;
}
