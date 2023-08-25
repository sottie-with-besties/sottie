package com.sottie.app.base.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@CreationTimestamp
	private LocalDateTime registerDate;
	@UpdateTimestamp
	private LocalDateTime modifyDate;
	@Builder.Default
	private String registeredBy = "system";
	@Builder.Default
	private String modifiedBy = "system";

}
