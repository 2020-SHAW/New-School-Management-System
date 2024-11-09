// src/main/java/com/management/school/services/CourseSubjectService.java
package com.admin.school.services;

import com.admin.school.entity.CourseSubject;
import com.admin.school.repository.CourseSubjectRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseSubjectService {

    private final CourseSubjectRepository repository;

    public CourseSubjectService(CourseSubjectRepository repository) {
        this.repository = repository;
    }

    public Optional<CourseSubject> get(Long id) {
        return repository.findById(id);
    }

    public CourseSubject save(CourseSubject courseSubject) {
        return repository.save(courseSubject);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
