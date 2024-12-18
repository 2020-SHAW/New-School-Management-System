// src/main/java/com/management/school/data/StudentRepository.java
package com.admin.school.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.admin.school.entity.Student;

public interface StudentRepository
        extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {

	List<Student> findByIsActiveTrue();

	List<Student> findByEnrollmentStatus(String enrollmentStatus);

}
