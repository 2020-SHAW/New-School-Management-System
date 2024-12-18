// src/main/java/com/management/school/services/FinanceService.java
package com.admin.school.services;

import com.admin.school.entity.Finance;
import com.admin.school.entity.Student;
import com.admin.school.repository.FinanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinanceService {

    private final FinanceRepository repository;

    public FinanceService(FinanceRepository repository) {
        this.repository = repository;
    }

    // Get a specific finance record by ID
    public Optional<Finance> get(Long id) {
        return repository.findById(id);
    }

    // Save a new finance record or update an existing one
    public Finance save(Finance finance) {
        return repository.save(finance);
    }

    // Delete a finance record by ID
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // List all finance records
    public List<Finance> listAll() {
        return repository.findAll();  // Retrieves all finance records from the database
    }

    public Finance getFinanceByStudent(Student student) {
        return repository.findByStudent(student);
    }

    public void saveFinance(Finance finance) {
        repository.save(finance);
    }
}
