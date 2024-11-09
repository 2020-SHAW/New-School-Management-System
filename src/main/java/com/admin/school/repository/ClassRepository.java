// src/main/java/com/management/school/data/ClassRepository.java
package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClassRepository
        extends JpaRepository<Class, Long>, JpaSpecificationExecutor<Class> {
}
