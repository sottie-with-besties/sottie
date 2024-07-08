package com.sottie.app.certification.model;

import com.sottie.app.friend.model.FriendProfile;
import com.sottie.app.user.model.Gender;
import com.sottie.app.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
public record Certification(

	String name,

	Gender gender, // TODO PortOne V1 KG이니시스에서 미지원, V2 에서 지원

	String phoneNumber,

	String identifier,

	String birthYear,

	boolean phoneAuthenticated
) {
	public User toUser() {
		return User.builder()
				.name(this.name())
				.phoneNumber(this.phoneNumber())
				.gender(this.gender())
				.birthYear(this.birthYear())
				.identifier(this.identifier())
				.phoneAuthenticated(this.phoneAuthenticated()).build();
	}
}
