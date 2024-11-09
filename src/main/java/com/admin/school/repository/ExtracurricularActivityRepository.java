// src/main/java/com/management/school/data/ExtracurricularActivityRepository.java
package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.school.entity.ExtracurricularActivity;

public interface ExtracurricularActivityRepository extends JpaRepository<ExtracurricularActivity, Long> {
}
