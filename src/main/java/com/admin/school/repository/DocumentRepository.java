// src/main/java/com/management/school/data/DocumentRepository.java
package com.admin.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.admin.school.entity.Document;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByDocumentName(String documentName);
}
