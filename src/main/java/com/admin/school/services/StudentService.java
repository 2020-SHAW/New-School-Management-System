package com.admin.school.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.school.entity.ParentGuardian;
import com.admin.school.entity.Student;
import com.admin.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Create or Update a student
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Find a student by ID
    public Optional<Student> getStudentById(String studentId) {
        return studentRepository.findById(studentId);
    }

    // Find all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Delete a student by ID
    public void deleteStudent(String studentId) {
        studentRepository.deleteById(studentId);
    }

    // Add a guardian to a student
    public void addGuardianToStudent(String studentId, ParentGuardian guardian) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.addGuardian(guardian);  // Add the guardian to the student's list of guardians
            studentRepository.save(student);  // Save the student (which will also save the guardian)
        } else {
            throw new IllegalArgumentException("Student not found with id: " + studentId);
        }
    }
}

