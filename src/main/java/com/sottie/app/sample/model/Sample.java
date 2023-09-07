package com.sottie.app.sample.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(schema = "sample")
public class Sample extends SampleBaseEntity {
	@Schema(name = "이름", description = "Sample 이름", example = "뽀로로")
	private String name;
	@Schema(name = "나이", description = "Sample 나이", example = "5")
	private Integer age;
	@Schema(name = "성별", description = "Sample 성별", examples = {"MALE", "FEMALE"})
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Schema(name = "사용_여부", description = "Sample 사용_여부", examples = {"true", "false"})
	private Boolean useYn;
}
