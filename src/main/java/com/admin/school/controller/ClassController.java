// src/main/java/com/admin/school/controller/ClassController.java
package com.admin.school.controller;

import com.admin.school.entity.Class;
import com.admin.school.services.ClassService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    // GET all classes with pagination
    @GetMapping
    public ResponseEntity<Page<Class>> getAllClasses(Pageable pageable) {
        Page<Class> classes = classService.list(pageable);
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    // GET class by ID
    @GetMapping("/{id}")
    public ResponseEntity<Class> getClassById(@PathVariable Long id) {
        Optional<Class> clazz = classService.get(id);
        return clazz.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST - create a new class
    @PostMapping
    public ResponseEntity<Class> createClass(@RequestBody Class clazz) {
        Class savedClass = classService.save(clazz);
        return new ResponseEntity<>(savedClass, HttpStatus.CREATED);
    }

    // PUT - update an existing class
    @PutMapping("/{id}")
    public ResponseEntity<Class> updateClass(@PathVariable Long id, @RequestBody Class clazz) {
        // Ensure the class exists before updating
        Optional<Class> existingClass = classService.get(id);
        if (existingClass.isPresent()) {
            clazz.setId(id); // Ensure the ID is set for updating
            Class updatedClass = classService.update(clazz);
            return new ResponseEntity<>(updatedClass, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - delete a class by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Long id) {
        Optional<Class> clazz = classService.get(id);
        if (clazz.isPresent()) {
            classService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET all classes with filtering and pagination (using Specification)
    @GetMapping("/filter")
    public ResponseEntity<Page<Class>> getFilteredClasses(Pageable pageable, Specification<Class> specification) {
        Page<Class> filteredClasses = classService.list(pageable, specification);
        return new ResponseEntity<>(filteredClasses, HttpStatus.OK);
    }

    // GET count of all classes
    @GetMapping("/count")
    public ResponseEntity<Integer> getClassCount() {
        int count = classService.count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
