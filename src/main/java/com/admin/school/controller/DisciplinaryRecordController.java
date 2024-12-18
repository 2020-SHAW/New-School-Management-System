package com.admin.school.controller;

import com.admin.school.entity.DisciplinaryRecord;
import com.admin.school.services.DisciplinaryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/disciplinary-records")
public class DisciplinaryRecordController {

    private final DisciplinaryRecordService disciplinaryRecordService;

    @Autowired
    public DisciplinaryRecordController(DisciplinaryRecordService disciplinaryRecordService) {
        this.disciplinaryRecordService = disciplinaryRecordService;
    }

    // Get all disciplinary records
    @GetMapping
    public ResponseEntity<List<DisciplinaryRecord>> getAllDisciplinaryRecords() {
        List<DisciplinaryRecord> disciplinaryRecords = disciplinaryRecordService.getAllDisciplinaryRecords();
        return ResponseEntity.ok(disciplinaryRecords);
    }

    // Get disciplinary record by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<DisciplinaryRecord>> getDisciplinaryRecordById(@PathVariable Long id) {
        Optional<DisciplinaryRecord> disciplinaryRecord = disciplinaryRecordService.getDisciplinaryRecordById(id);
        return disciplinaryRecord != null ? ResponseEntity.ok(disciplinaryRecord) : ResponseEntity.notFound().build();
    }

    // Create or update disciplinary record
    @PostMapping
    public ResponseEntity<DisciplinaryRecord> createOrUpdateDisciplinaryRecord(@RequestBody DisciplinaryRecord disciplinaryRecord) {
        DisciplinaryRecord savedDisciplinaryRecord = disciplinaryRecordService.createOrUpdateDisciplinaryRecord(disciplinaryRecord);
        return ResponseEntity.ok(savedDisciplinaryRecord);
    }

    // Delete disciplinary record by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplinaryRecord(@PathVariable Long id) {
        boolean isDeleted = disciplinaryRecordService.deleteDisciplinaryRecord(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
