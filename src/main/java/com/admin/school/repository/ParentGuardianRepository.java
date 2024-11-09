// src/main/java/com/management/school/data/ParentGuardianRepository.java
package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.admin.school.entity.ParentGuardian;

public interface ParentGuardianRepository
        extends JpaRepository<ParentGuardian, Long>, JpaSpecificationExecutor<ParentGuardian> {
}
