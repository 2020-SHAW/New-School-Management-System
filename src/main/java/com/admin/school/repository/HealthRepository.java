// src/main/java/com/management/school/repository/HealthRepository.java
package com.admin.school.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.school.entity.Health;

@Repository
public interface HealthRepository extends JpaRepository<Health, Long> {

    // Find health records by student ID
    List<Health> findByStudentId(String studentId);

    // Find health records by teacher ID
    List<Health> findByTeacherId(String teacherId);
}
