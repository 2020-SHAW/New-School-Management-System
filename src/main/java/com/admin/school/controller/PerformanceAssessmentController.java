// src/main/java/com/management/school/controller/PerformanceAssessmentController.java
package com.admin.school.controller;

import com.admin.school.entity.PerformanceAssessment;
import com.admin.school.services.PerformanceAssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/performance-assessments")
public class PerformanceAssessmentController {

    private final PerformanceAssessmentService performanceAssessmentService;

    public PerformanceAssessmentController(PerformanceAssessmentService performanceAssessmentService) {
        this.performanceAssessmentService = performanceAssessmentService;
    }

    // CREATE: Create a new performance assessment
    @PostMapping
    public ResponseEntity<PerformanceAssessment> createPerformanceAssessment(@RequestBody PerformanceAssessment performanceAssessment) {
        PerformanceAssessment savedPerformanceAssessment = performanceAssessmentService.save(performanceAssessment);
        return new ResponseEntity<>(savedPerformanceAssessment, HttpStatus.CREATED);
    }

    // READ: Get a performance assessment by ID
    @GetMapping("/{id}")
    public ResponseEntity<PerformanceAssessment> getPerformanceAssessmentById(@PathVariable Long id) {
        Optional<PerformanceAssessment> performanceAssessment = performanceAssessmentService.get(id);
        return performanceAssessment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // READ: Get performance assessment by student ID
    @GetMapping("/student/{studentId}")
    public ResponseEntity<PerformanceAssessment> getPerformanceAssessmentByStudentId(@PathVariable Long studentId) {
        Optional<PerformanceAssessment> performanceAssessment = performanceAssessmentService.getByStudentId(studentId);
        return performanceAssessment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // READ: Get all performance assessments
    @GetMapping
    public ResponseEntity<List<PerformanceAssessment>> getAllPerformanceAssessments() {
        List<PerformanceAssessment> assessments = performanceAssessmentService.listAll();
        return new ResponseEntity<>(assessments, HttpStatus.OK);
    }

    // UPDATE: Update a performance assessment by ID
    @PutMapping("/{id}")
    public ResponseEntity<PerformanceAssessment> updatePerformanceAssessment(@PathVariable Long id, @RequestBody PerformanceAssessment performanceAssessment) {
        Optional<PerformanceAssessment> existingAssessment = performanceAssessmentService.get(id);
        if (existingAssessment.isPresent()) {
            performanceAssessment.setId(id);  // Ensure the ID is set for the update
            PerformanceAssessment updatedAssessment = performanceAssessmentService.save(performanceAssessment);
            return ResponseEntity.ok(updatedAssessment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE: Delete a performance assessment by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerformanceAssessment(@PathVariable Long id) {
        Optional<PerformanceAssessment> performanceAssessment = performanceAssessmentService.get(id);
        if (performanceAssessment.isPresent()) {
            performanceAssessmentService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
