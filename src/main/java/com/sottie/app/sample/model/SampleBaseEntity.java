package com.sottie.app.sample.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class SampleBaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(name = "id", description = "id", example = "1")
	private Long id;
	@CreationTimestamp
	@Schema(name = "등록_일자", description = "등록_일자", exampleClasses = {LocalDateTime.class})
	private LocalDateTime registerDate;
	@UpdateTimestamp
	@Schema(name = "수정_일자", description = "수정_일자", exampleClasses = {LocalDateTime.class})
	private LocalDateTime modifyDate;
	@Builder.Default
	@Schema(name = "등록_아이디", description = "등록_아이디", example = "1")
	private Long registerId = -1L;
	@Builder.Default
	@Schema(name = "수정_아이디", description = "수정_아이디", example = "1")
	private Long modifyId = -1L;
}
