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

@Entity
@Getter
@Builder
@Table(name = "st_friend")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Friend extends BaseEntity {

	private Long userId;

	private Long friendId;

	@Column(length = 30)
	private String alias;

	private boolean blocked;
}
