// src/main/java/com/admin/school/controller/DisciplinaryRecordController.java
package com.admin.school.controller;

import com.admin.school.entity.DisciplinaryRecord;
import com.admin.school.entity.Student;
import com.admin.school.services.DisciplinaryRecordService;
import com.admin.school.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/disciplinaryRecords")
public class DisciplinaryRecordController {

    private final DisciplinaryRecordService disciplinaryRecordService;
    private final StudentService studentService; // Injecting StudentService to check if student exists

    // Constructor to inject the services
    public DisciplinaryRecordController(DisciplinaryRecordService disciplinaryRecordService, StudentService studentService) {
        this.disciplinaryRecordService = disciplinaryRecordService;
        this.studentService = studentService;
    }

    // GET a disciplinary record by ID
    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaryRecord> getDisciplinaryRecordById(@PathVariable Long id) {
        Optional<DisciplinaryRecord> disciplinaryRecord = disciplinaryRecordService.get(id);
        return disciplinaryRecord.map(ResponseEntity::ok)
                                 .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST - create a new disciplinary record
    @PostMapping
    public ResponseEntity<DisciplinaryRecord> createDisciplinaryRecord(@RequestBody DisciplinaryRecord disciplinaryRecord) {
        // Validate that the student exists before saving the record
        Optional<Student> student = studentService.get(disciplinaryRecord.getStudent().getId());
        if (student.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null); // Student not found, return 404
        }

        // Associate the student to the disciplinary record
        disciplinaryRecord.setStudent(student.get());

        // Save the disciplinary record
        DisciplinaryRecord savedRecord = disciplinaryRecordService.update(disciplinaryRecord);
        return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
    }

    // PUT - update an existing disciplinary record by ID
    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaryRecord> updateDisciplinaryRecord(@PathVariable Long id, @RequestBody DisciplinaryRecord disciplinaryRecord) {
        // Check if the disciplinary record exists
        Optional<DisciplinaryRecord> existingRecord = disciplinaryRecordService.get(id);
        if (existingRecord.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // If not found, return 404
        }

        // Check if the student exists before updating
        Optional<Student> student = studentService.get(disciplinaryRecord.getStudent().getId());
        if (student.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null); // If student doesn't exist, return 404
        }

        // Set the student to the disciplinary record before updating
        disciplinaryRecord.setId(id); // Ensure the ID is correct for updating
        disciplinaryRecord.setStudent(student.get());

        // Save the updated disciplinary record
        DisciplinaryRecord updatedRecord = disciplinaryRecordService.update(disciplinaryRecord);
        return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
    }

    // DELETE - delete a disciplinary record by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplinaryRecord(@PathVariable Long id) {
        Optional<DisciplinaryRecord> disciplinaryRecord = disciplinaryRecordService.get(id);
        if (disciplinaryRecord.isPresent()) {
            disciplinaryRecordService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Successfully deleted
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Record not found, return 404
        }
    }
}
