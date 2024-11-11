package com.admin.school.repository;

import com.admin.school.entity.Class; // Add this import

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClassRepository extends JpaRepository<com.admin.school.entity.Class, Long>, JpaSpecificationExecutor<com.admin.school.entity.Class> {
	List<Class> findByNameContainingIgnoreCase(String name);
    boolean existsByNameAndGrade(String name, String grade);
    
}