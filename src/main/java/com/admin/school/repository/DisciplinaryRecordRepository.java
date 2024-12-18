package com.admin.school.repository;

import com.admin.school.entity.DisciplinaryRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaryRecordRepository extends JpaRepository<DisciplinaryRecord, Long> {
    
    // Method to find disciplinary records by student ID
    List<DisciplinaryRecord> findByStudentId(String studentId);

	List<DisciplinaryRecord> findByTeacherId(String teacherId);
}
