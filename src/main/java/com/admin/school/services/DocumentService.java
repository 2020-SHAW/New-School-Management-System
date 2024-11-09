// src/main/java/com/management/school/services/DocumentService.java
package com.admin.school.services;

import com.admin.school.entity.Document;
import com.admin.school.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentService {

    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    public Document save(String documentName, String fileType, byte[] fileData) {
        Document document = new Document();
        document.setDocumentName(documentName);
        document.setFileType(fileType);
        document.setFileData(fileData);
        return repository.save(document);
    }

    public Optional<Document> get(Long id) {
        return repository.findById(id);
    }

    public Optional<Document> getByName(String documentName) {
        return repository.findByDocumentName(documentName);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
