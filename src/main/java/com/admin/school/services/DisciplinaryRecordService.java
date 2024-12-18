package com.admin.school.services;

import com.admin.school.entity.DisciplinaryRecord;
import com.admin.school.repository.DisciplinaryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaryRecordService {

    private final DisciplinaryRecordRepository disciplinaryRecordRepository;

    @Autowired
    public DisciplinaryRecordService(DisciplinaryRecordRepository disciplinaryRecordRepository) {
        this.disciplinaryRecordRepository = disciplinaryRecordRepository;
    }

    /**
     * Get all disciplinary records.
     * @return List of all disciplinary records.
     */
    public List<DisciplinaryRecord> getAllDisciplinaryRecords() {
        return disciplinaryRecordRepository.findAll();
    }

    /**
     * Get a disciplinary record by ID.
     * @param id The ID of the disciplinary record.
     * @return An Optional containing the disciplinary record, or empty if not found.
     */
    public Optional<DisciplinaryRecord> getDisciplinaryRecordById(Long id) {
        return disciplinaryRecordRepository.findById(id);
    }

    /**
     * Create or update a disciplinary record.
     * @param disciplinaryRecord The disciplinary record to be created or updated.
     * @return The created or updated disciplinary record.
     */
    public DisciplinaryRecord createOrUpdateDisciplinaryRecord(DisciplinaryRecord disciplinaryRecord) {
        return disciplinaryRecordRepository.save(disciplinaryRecord);
    }

    /**
     * Delete a disciplinary record by ID.
     * @param id The ID of the disciplinary record to be deleted.
     * @return true if the record was deleted, false if not found.
     */
    public boolean deleteDisciplinaryRecord(Long id) {
        if (disciplinaryRecordRepository.existsById(id)) {
            disciplinaryRecordRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Get disciplinary records for a specific student.
     * @param studentId The ID of the student.
     * @return List of disciplinary records related to the student.
     */
    public List<DisciplinaryRecord> getDisciplinaryRecordsByStudentId(String studentId) {
        return disciplinaryRecordRepository.findByStudentId(studentId);
    }

    /**
     * Get disciplinary records for a specific teacher.
     * @param teacherId The ID of the teacher.
     * @return List of disciplinary records related to the teacher.
     */
    public List<DisciplinaryRecord> getDisciplinaryRecordsByTeacherId(String teacherId) {
        return disciplinaryRecordRepository.findByTeacherId(teacherId);
    }
}
