package com.admin.school.services;

import com.admin.school.entity.PerformanceAssessment;
import com.admin.school.repository.PerformanceAssessmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerformanceAssessmentService {

    private final PerformanceAssessmentRepository repository;

    public PerformanceAssessmentService(PerformanceAssessmentRepository repository) {
        this.repository = repository;
    }

    public Optional<PerformanceAssessment> get(Long id) {
        return repository.findById(id);
    }

    public Optional<PerformanceAssessment> getByStudentId(Long studentId) {
        return repository.findById(studentId);
    }

    public PerformanceAssessment save(PerformanceAssessment performanceAssessment) {
        return repository.save(performanceAssessment);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<PerformanceAssessment> listAll() {
        return repository.findAll();
    }
}
