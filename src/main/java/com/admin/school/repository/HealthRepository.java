// src/main/java/com/management/school/data/HealthRepository.java
package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.school.entity.Health;

public interface HealthRepository extends JpaRepository<Health, Long> {
}
