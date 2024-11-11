// src/main/java/com/management/school/data/PerformanceAssessmentRepository.java
package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.school.entity.PerformanceAssessment;

import java.util.Optional;

public interface PerformanceAssessmentRepository extends JpaRepository<PerformanceAssessment, Long> {
    Optional<PerformanceAssessment> findByStudentId(String studentId);  // <-- Change Long to String
}
