// src/main/java/com/management/school/controller/ExtracurricularActivityController.java
package com.admin.school.controller;

import com.admin.school.entity.ExtracurricularActivity;
import com.admin.school.services.ExtracurricularActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/extracurricular-activities")
public class ExtracurricularActivityController {

    private final ExtracurricularActivityService activityService;

    public ExtracurricularActivityController(ExtracurricularActivityService activityService) {
        this.activityService = activityService;
    }

    // POST - Create a new extracurricular activity
    @PostMapping
    public ResponseEntity<ExtracurricularActivity> createActivity(@RequestBody ExtracurricularActivity activity) {
        ExtracurricularActivity savedActivity = activityService.save(activity);
        return new ResponseEntity<>(savedActivity, HttpStatus.CREATED);
    }

    // GET - Get an extracurricular activity by ID
    @GetMapping("/{id}")
    public ResponseEntity<ExtracurricularActivity> getActivityById(@PathVariable Long id) {
        Optional<ExtracurricularActivity> activity = activityService.get(id);
        return activity.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // GET - Get a list of all extracurricular activities
    @GetMapping
    public List<ExtracurricularActivity> getAllActivities() {
        return activityService.listAll();
    }

    // PUT - Update an existing extracurricular activity
    @PutMapping("/{id}")
    public ResponseEntity<ExtracurricularActivity> updateActivity(@PathVariable Long id, @RequestBody ExtracurricularActivity activity) {
        Optional<ExtracurricularActivity> existingActivity = activityService.get(id);
        if (existingActivity.isPresent()) {
            // DO NOT set the ID manually here. Spring Data JPA will handle it.
            activity.setId(id); // The ID is required for JPA to know which record to update.
            ExtracurricularActivity updatedActivity = activityService.update(activity);
            return ResponseEntity.ok(updatedActivity);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE - Delete an extracurricular activity by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        Optional<ExtracurricularActivity> activity = activityService.get(id);
        if (activity.isPresent()) {
            activityService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
