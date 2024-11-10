// src/main/java/com/admin/school/controller/ParentGuardianController.java
package com.admin.school.controller;

import com.admin.school.entity.ParentGuardian;
import com.admin.school.services.ParentGuardianService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parents")
public class ParentGuardianController {

    private final ParentGuardianService parentGuardianService;

    public ParentGuardianController(ParentGuardianService parentGuardianService) {
        this.parentGuardianService = parentGuardianService;
    }

    // GET all parents with pagination
    @GetMapping
    public ResponseEntity<Page<ParentGuardian>> getAllParents(Pageable pageable) {
        Page<ParentGuardian> parents = parentGuardianService.list(pageable);
        return new ResponseEntity<>(parents, HttpStatus.OK);
    }

    // GET all parents for dropdown or other views (no pagination)
    @GetMapping("/all")
    public ResponseEntity<List<ParentGuardian>> getAllParents() {
        List<ParentGuardian> parents = parentGuardianService.getAllParents();
        return new ResponseEntity<>(parents, HttpStatus.OK);
    }

    // GET parent by ID
    @GetMapping("/{id}")
    public ResponseEntity<ParentGuardian> getParentById(@PathVariable Long id) {
        Optional<ParentGuardian> parent = parentGuardianService.get(id);
        return parent.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST - create a new parent
    @PostMapping
    public ResponseEntity<ParentGuardian> createParent(@RequestBody ParentGuardian parentGuardian) {
        ParentGuardian savedParent = parentGuardianService.save(parentGuardian);
        return new ResponseEntity<>(savedParent, HttpStatus.CREATED);
    }

    // PUT - update an existing parent
    @PutMapping("/{id}")
    public ResponseEntity<ParentGuardian> updateParent(@PathVariable Long id, @RequestBody ParentGuardian parentGuardian) {
        // Ensure the parent exists before updating
        Optional<ParentGuardian> existingParent = parentGuardianService.get(id);
        if (existingParent.isPresent()) {
            parentGuardian.setId(id); // Ensure the ID is set for updating
            ParentGuardian updatedParent = parentGuardianService.update(parentGuardian);
            return new ResponseEntity<>(updatedParent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - delete a parent by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        Optional<ParentGuardian> parent = parentGuardianService.get(id);
        if (parent.isPresent()) {
            parentGuardianService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET all parents with filtering and pagination (using Specification)
    @GetMapping("/filter")
    public ResponseEntity<Page<ParentGuardian>> getFilteredParents(Pageable pageable, Specification<ParentGuardian> specification) {
        Page<ParentGuardian> filteredParents = parentGuardianService.list(pageable, specification);
        return new ResponseEntity<>(filteredParents, HttpStatus.OK);
    }

    // GET count of all parents
    @GetMapping("/count")
    public ResponseEntity<Integer> getParentCount() {
        int count = parentGuardianService.count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
