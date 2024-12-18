package com.admin.school.repository;

import com.admin.school.entity.Finance;
import com.admin.school.entity.Student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinanceRepository extends JpaRepository<Finance, Long> {
    List<Finance> findByStudentId(String studentId);

    Finance findByStudent(Student student); 
}
