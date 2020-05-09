package com.github.johnmedlockdev.project1john.repositories;

import com.github.johnmedlockdev.project1john.models.SparkModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SparkRepository  extends JpaRepository<SparkModel, Long> {
}
