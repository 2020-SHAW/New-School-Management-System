package com.admin.school.repository;

import com.admin.school.entity.ParentGuardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ParentGuardianRepository extends JpaRepository<ParentGuardian, String>, JpaSpecificationExecutor<ParentGuardian> {
    // Custom query methods can be added here
}
