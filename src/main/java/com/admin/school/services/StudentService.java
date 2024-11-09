// src/main/java/com/management/school/services/StudentService.java
package com.admin.school.services;

import com.admin.school.entity.Class;
import com.admin.school.repository.ClassRepository;
import com.admin.school.entity.Student;
import com.admin.school.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;

    public StudentService(StudentRepository studentRepository, ClassRepository classRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
    }

    public Optional<Student> get(Long id) {
        return studentRepository.findById(id);
    }

    public Student save(Student entity) {
        return studentRepository.save(entity);
    }

    public Student update(Student entity) {
        return studentRepository.save(entity);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    public Page<Student> list(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public Page<Student> list(Pageable pageable, Specification<Student> filter) {
        return studentRepository.findAll(filter, pageable);
    }

    public int count() {
        return (int) studentRepository.count();
    }

    // Method to retrieve all classes for the dropdown in StudentView
    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }
    

}
