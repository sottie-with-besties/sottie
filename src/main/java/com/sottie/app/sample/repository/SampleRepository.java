package com.sottie.app.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sottie.app.sample.model.Sample;

@Repository
public interface SampleRepository extends JpaRepository<Sample, Long> {
}
