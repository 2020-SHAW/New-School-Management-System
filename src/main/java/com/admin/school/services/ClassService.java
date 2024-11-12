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

    // Filter classes by name (existing method)
    public List<Class> filterByName(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    // Check if a class exists by name and grade
    public boolean existsByNameAndGrade(String name, String grade) {
        return repository.existsByNameAndGrade(name, grade);
    }

    // Real-time search functionality by both name and grade (new method)
    public Page<Class> findBySearchTerm(String searchTerm, Pageable pageable) {
        Specification<Class> specification = (root, query, criteriaBuilder) -> {
            return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchTerm.toLowerCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("grade")), "%" + searchTerm.toLowerCase() + "%")
            );
        };
        return repository.findAll(specification, pageable);
    }
}
