package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.school.entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    // No need for custom query methods unless needed
}
