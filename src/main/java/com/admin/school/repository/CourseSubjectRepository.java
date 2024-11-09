// src/main/java/com/management/school/data/CourseSubjectRepository.java
package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.school.entity.CourseSubject;

public interface CourseSubjectRepository extends JpaRepository<CourseSubject, Long> {
}
