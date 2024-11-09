// src/main/java/com/management/school/data/FinanceRepository.java
package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.school.entity.Finance;

public interface FinanceRepository extends JpaRepository<Finance, Long> {
}
