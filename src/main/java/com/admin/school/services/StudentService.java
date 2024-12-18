package com.admin.school.services;

import com.admin.school.entity.Class;
import com.admin.school.entity.Health;
import com.admin.school.repository.ClassRepository;
import com.admin.school.entity.Student;
import com.admin.school.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // Get a student by ID (Updated to accept Long type ID instead of String)
    public Optional<Student> get(String id) {
        return studentRepository.findById(id);
    }

    // Save a new student or update an existing student
    public Student save(Student entity) {
        return studentRepository.save(entity);
    }

    // Update a student's information (same as save since it will update if entity already exists)
    public Student update(Student entity) {
        return studentRepository.save(entity);
    }

    // Delete a student by ID
    @Transactional
    public void delete(String id) {
        studentRepository.deleteById(id);
    }

    // List students with pagination support
    public Page<Student> list(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    // List students with pagination and optional filtering
    public Page<Student> list(Pageable pageable, Specification<Student> filter) {
        return studentRepository.findAll(filter, pageable);
    }

    // Count the total number of students
    public int count() {
        return (int) studentRepository.count();
    }

    // Method to retrieve all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();  // Fetch all students from the repository
    }

    // Method to retrieve all classes (used for dropdown in StudentView)
    public List<Class> getAllClasses() {
        return classRepository.findAll();
    }

    // Method to retrieve students by enrollment status
    public List<Student> getStudentsByEnrollmentStatus(String enrollmentStatus) {
        return studentRepository.findByEnrollmentStatus(enrollmentStatus);
    }

    // Method to retrieve all active students (students with isActive = true)
    public List<Student> getActiveStudents() {
        return studentRepository.findByIsActiveTrue();
    }

    // Method to deactivate a student (expulsion or any other reason)
    @Transactional
    public void deactivateStudent(String id) {
        Optional<Student> studentOpt = studentRepository.findById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.deactivate();  // Calls the deactivate method in the Student entity
            studentRepository.save(student);  // Persist the deactivation
        } else {
            throw new RuntimeException("Student not found with id: " + id);
        }
    }

    // Method to assign a student to a specific class
    @Transactional
    public void assignStudentToClass(String studentId, Long classId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Class> classOpt = classRepository.findById(classId);

        if (studentOpt.isPresent() && classOpt.isPresent()) {
            Student student = studentOpt.get();
            Class assignedClass = classOpt.get();
            student.setAssignedClass(assignedClass);
            studentRepository.save(student);  // Save the updated student with new class assignment
        } else {
            throw new RuntimeException("Student or Class not found with the provided IDs.");
        }
    }

    // Method to update the student's health records
    @Transactional
    public void updateHealthRecords(String studentId, List<Health> healthRecords) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setHealthRecords(healthRecords);  // Update the health records of the student
            studentRepository.save(student);  // Persist the changes
        } else {
            throw new RuntimeException("Student not found with id: " + studentId);
        }
    }
}
