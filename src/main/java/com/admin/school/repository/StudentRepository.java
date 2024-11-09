package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.school.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    // JpaRepository provides built-in methods like save(), findById(), findAll(), deleteById(), etc.
    // You can add custom queries if necessary, e.g., findByClassEntity(), etc.
}
