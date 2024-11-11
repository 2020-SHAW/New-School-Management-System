package com.admin.school.services;

import com.admin.school.entity.Teacher;
import com.admin.school.repository.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    // Constructor injection is sufficient; no need for a default constructor
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // List teachers with pagination
    public Page<Teacher> listTeachers(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    // List all teachers without pagination (use with caution for large data sets)
    public List<Teacher> listAllTeachers() {
        return teacherRepository.findAll();
    }

    // Get teacher by ID, handle empty result gracefully
    public Optional<Teacher> getTeacherById(String id) {
        return teacherRepository.findById(id);
    }

    // Create or update teacher information
    @Transactional
    public Teacher createOrUpdateTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // Delete teacher by ID
    @Transactional
    public void deleteTeacherById(String id) {
        teacherRepository.deleteById(id);
    }

    // Get the total count of teachers
    public long getTeacherCount() {
        return teacherRepository.count();
    }
}
