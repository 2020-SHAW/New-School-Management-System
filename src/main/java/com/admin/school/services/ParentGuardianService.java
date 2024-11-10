// src/main/java/com/management/school/services/ParentGuardianService.java
package com.admin.school.services;

import com.admin.school.entity.ParentGuardian;
import com.admin.school.repository.ParentGuardianRepository;
import com.vaadin.flow.data.provider.DataProvider;

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

    public Optional<ParentGuardian> get(Long id) {
        return repository.findById(id);
    }

    public ParentGuardian save(ParentGuardian entity) {
        return repository.save(entity);
    }

    public ParentGuardian update(ParentGuardian entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<ParentGuardian> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<ParentGuardian> list(Pageable pageable, Specification<ParentGuardian> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    // Method to retrieve all ParentGuardians for the dropdown in StudentView
    public List<ParentGuardian> getAllParents() {
        return repository.findAll();
    }

	public DataProvider<ParentGuardian, Void> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
