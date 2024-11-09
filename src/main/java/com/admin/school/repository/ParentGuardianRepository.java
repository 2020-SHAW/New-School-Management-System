package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.school.entity.ParentGuardian;


@Repository
public interface ParentGuardianRepository extends JpaRepository<ParentGuardian, String> {
    // JpaRepository provides built-in methods like save(), findById(), findAll(), deleteById(), etc.
    // You can add custom queries if necessary, e.g., findByPhoneNumber(), etc.
}
