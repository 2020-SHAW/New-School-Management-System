package com.admin.school.services;

import com.admin.school.entity.Teacher;
import com.admin.school.repository.TeacherRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    
    public Page<Teacher> listTeachers(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    // Optionally, if you need to get all teachers without pagination
    public List<Teacher> listAll() {
        return teacherRepository.findAll();
    }

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // Get all teachers
    public Iterable<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    // Get teacher by ID
    public Optional<Teacher> getTeacherById(String id) {
        return teacherRepository.findById(id);
    }

    // Create or update teacher
    public Teacher createOrUpdateTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    // Delete teacher by ID
    public void deleteTeacherById(String id) {
        teacherRepository.deleteById(id);
    }

    // Get count of teachers
    public long getTeacherCount() {
        return teacherRepository.count();
    }
}
