// src/main/java/com/management/school/services/FinanceService.java
package com.admin.school.services;

import com.admin.school.entity.Finance;
import com.admin.school.repository.FinanceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FinanceService {

    private final FinanceRepository repository;

    public FinanceService(FinanceRepository repository) {
        this.repository = repository;
    }

    public Optional<Finance> get(Long id) {
        return repository.findById(id);
    }

    public Finance save(Finance finance) {
        return repository.save(finance);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
