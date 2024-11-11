package com.admin.school.services;

import com.admin.school.entity.ParentGuardian;
import com.admin.school.repository.ParentGuardianRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentGuardianService {

    private final ParentGuardianRepository repository;

    public ParentGuardianService(ParentGuardianRepository repository) {
        this.repository = repository;
    }

    public Optional<ParentGuardian> get(String id) {
        return repository.findById(id);
    }

    public ParentGuardian save(ParentGuardian entity) {
        return repository.save(entity);
    }

    public ParentGuardian update(ParentGuardian entity) {
        return repository.save(entity);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public List<ParentGuardian> findAll() {
        return repository.findAll();
    }

    public List<ParentGuardian> getAllParents() {
        return repository.findAll();
    }

    // List with pagination and filtering (Specification)
    public Page<ParentGuardian> list(Pageable pageable, Specification<ParentGuardian> specification) {
        return repository.findAll(specification, pageable);  // This will now work correctly
    }

    public long count() {
        return repository.count();
    }
}
