package com.sottie.app.sample.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sottie.app.sample.model.Sample;
import com.sottie.app.sample.repository.SampleRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SampleService {

	private final SampleRepository sampleRepository;

	public Optional<Sample> test() {
		return sampleRepository.findById(1L);
	}
}
