// src/main/java/com/management/school/controller/DocumentController.java
package com.admin.school.controller;

import com.admin.school.entity.Document;
import com.admin.school.services.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    // POST - Upload a new document
    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(@RequestParam String documentName,
                                                   @RequestParam String fileType,
                                                   @RequestParam MultipartFile file) {
        try {
            // Convert MultipartFile to byte array
            byte[] fileData = file.getBytes();
            
            // Save the document
            Document savedDocument = documentService.save(documentName, fileType, fileData);
            return new ResponseEntity<>(savedDocument, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET - Get a document by ID
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Optional<Document> document = documentService.get(id);
        return document.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // GET - Get a document by name
    @GetMapping("/name/{documentName}")
    public ResponseEntity<Document> getDocumentByName(@PathVariable String documentName) {
        Optional<Document> document = documentService.getByName(documentName);
        return document.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // DELETE - Delete a document by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        Optional<Document> document = documentService.get(id);
        if (document.isPresent()) {
            documentService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Successfully deleted
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Document not found
        }
    }
}
