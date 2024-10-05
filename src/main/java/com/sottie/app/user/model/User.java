package com.sottie.app.user.model;

import com.sottie.app.base.domain.BaseEntity;

import com.sottie.app.gathering.model.GatheringUser;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "st_user")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class User extends BaseEntity {

	private String name;
	@NotBlank
	@Email
	private String email;

	@Builder.Default
	private String nickName = "System";

	@Setter
	@NotBlank
	private String password;

	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String identifier;

	private String birthYear;

	private boolean phoneAuthenticated;

	@OneToMany(mappedBy = "user")
	private List<GatheringUser> gatheringUsers;
}
