// src/main/java/com/management/school/services/HealthService.java
package com.admin.school.services;

import com.admin.school.entity.Health;
import com.admin.school.repository.HealthRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HealthService {

    private final HealthRepository repository;

    public HealthService(HealthRepository repository) {
        this.repository = repository;
    }

    public Optional<Health> get(Long id) {
        return repository.findById(id);
    }

    public Health save(Health health) {
        return repository.save(health);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
