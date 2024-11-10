// src/main/java/com/management/school/controller/HealthController.java
package com.admin.school.controller;

import com.admin.school.entity.Health;
import com.admin.school.services.HealthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    // CREATE: Create a new health record
    @PostMapping
    public ResponseEntity<Health> createHealth(@RequestBody Health health) {
        Health savedHealth = healthService.save(health);
        return new ResponseEntity<>(savedHealth, HttpStatus.CREATED);
    }

    // READ: Get a health record by ID
    @GetMapping("/{id}")
    public ResponseEntity<Health> getHealthById(@PathVariable Long id) {
        Optional<Health> health = healthService.get(id);
        return health.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // READ: Get all health records
    @GetMapping
    public ResponseEntity<List<Health>> getAllHealthRecords() {
        List<Health> healthRecords = healthService.listAll();
        return new ResponseEntity<>(healthRecords, HttpStatus.OK);
    }

    // UPDATE: Update a health record by ID
    @PutMapping("/{id}")
    public ResponseEntity<Health> updateHealth(@PathVariable Long id, @RequestBody Health health) {
        Optional<Health> existingHealth = healthService.get(id);
        if (existingHealth.isPresent()) {
            health.setId(id);  // Set the existing ID for the update
            Health updatedHealth = healthService.save(health);
            return ResponseEntity.ok(updatedHealth);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE: Delete a health record by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealth(@PathVariable Long id) {
        Optional<Health> health = healthService.get(id);
        if (health.isPresent()) {
            healthService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
