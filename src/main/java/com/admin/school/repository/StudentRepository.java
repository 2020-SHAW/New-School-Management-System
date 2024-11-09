// src/main/java/com/management/school/data/StudentRepository.java
package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.admin.school.entity.Student;

public interface StudentRepository
        extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

}
