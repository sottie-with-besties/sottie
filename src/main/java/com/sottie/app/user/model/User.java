package com.sottie.app.user.model;

import com.sottie.app.base.domain.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "st_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

	private String name;

	private String email;

	private String nickName;

	private String password;

	private String phoneNumber;
	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String identifier;

	private String birthYear;

	private boolean phoneAuthenticated;
}
