package com.sottie.app.friend.model;

import com.sottie.app.base.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@Table(name = "st_friend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Friend extends BaseEntity {

	private Long userId;

	private Long friendId;

	@Setter
	@Column(length = 30)
	private String alias;

	@Setter
	private boolean blocked;
}
