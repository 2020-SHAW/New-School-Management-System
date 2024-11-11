// src/main/java/com/admin/school/services/ClassService.java
package com.admin.school.services;

import com.admin.school.entity.Class;
import com.admin.school.repository.ClassRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    private final ClassRepository repository;

    public ClassService(ClassRepository repository) {
        this.repository = repository;
    }

    // Get a class by its ID
    public Optional<Class> get(Long id) {
        return repository.findById(id);
    }

    // Save or update a class
    public Class save(Class clazz) {
        return repository.save(clazz);
    }

    // Update a class (Same as save in JPA)
    public Class update(Class clazz) {
        return repository.save(clazz);
    }

    // Delete a class by its ID
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // List all classes with pagination support
    public Page<Class> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // List all classes without pagination
    public List<Class> listAll() {
        return repository.findAll(); // Retrieves all classes
    }

    // List all classes with filtering and pagination (using Specification)
    public Page<Class> list(Pageable pageable, Specification<Class> specification) {
        return repository.findAll(specification, pageable);
    }

    // Get count of all classes
    public long count() {
        return repository.count();
    }

    // Filter classes by name
    public List<Class> filterByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
