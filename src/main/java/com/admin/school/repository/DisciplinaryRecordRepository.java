// src/main/java/com/management/school/data/DisciplinaryRecordRepository.java
package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.admin.school.entity.DisciplinaryRecord;

public interface DisciplinaryRecordRepository extends JpaRepository<DisciplinaryRecord, Long>, JpaSpecificationExecutor<DisciplinaryRecord> {
}
