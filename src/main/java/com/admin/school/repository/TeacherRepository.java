// src/main/java/com/management/school/data/TeacherRepository.java
package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.admin.school.entity.Teacher;

public interface TeacherRepository
        extends JpaRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {
}
