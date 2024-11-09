// src/main/java/com/management/school/services/DisciplinaryRecordService.java
package com.admin.school.services;

import com.admin.school.entity.DisciplinaryRecord;
import com.admin.school.repository.DisciplinaryRecordRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DisciplinaryRecordService {
    private final DisciplinaryRecordRepository repository;

    public DisciplinaryRecordService(DisciplinaryRecordRepository repository) {
        this.repository = repository;
    }

    public Optional<DisciplinaryRecord> get(Long id) {
        return repository.findById(id);
    }

    public DisciplinaryRecord update(DisciplinaryRecord entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
