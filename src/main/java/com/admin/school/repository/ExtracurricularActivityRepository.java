// src/main/java/com/management/school/repository/ExtracurricularActivityRepository.java
package com.admin.school.repository;

import com.admin.school.entity.ExtracurricularActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtracurricularActivityRepository extends JpaRepository<ExtracurricularActivity, Long> {
    // No need for custom methods unless necessary, JpaRepository provides CRUD functionality
}
