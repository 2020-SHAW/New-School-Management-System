// src/main/java/com/management/school/services/ExtracurricularActivityService.java
package com.admin.school.services;

import com.admin.school.entity.ExtracurricularActivity;
import com.admin.school.repository.ExtracurricularActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExtracurricularActivityService {

    private final ExtracurricularActivityRepository repository;

    public ExtracurricularActivityService(ExtracurricularActivityRepository repository) {
        this.repository = repository;
    }

    // Save a new extracurricular activity
    public ExtracurricularActivity save(ExtracurricularActivity activity) {
        return repository.save(activity);
    }

    // Get an extracurricular activity by ID
    public Optional<ExtracurricularActivity> get(Long id) {
        return repository.findById(id);
    }

    // Get a list of all extracurricular activities
    public List<ExtracurricularActivity> listAll() {
        return repository.findAll();
    }

    // Update an existing extracurricular activity
    public ExtracurricularActivity update(ExtracurricularActivity activity) {
        return repository.save(activity);
    }

    // Delete an extracurricular activity by ID
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
