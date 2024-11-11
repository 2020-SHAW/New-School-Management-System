// src/main/java/com/admin/school/controller/StudentController.java
package com.admin.school.controller;

import com.admin.school.entity.Class;
import com.admin.school.entity.Student;
import com.admin.school.services.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // GET all students with pagination
    @GetMapping
    public ResponseEntity<Page<Student>> getAllStudents(Pageable pageable) {
        Page<Student> students = studentService.list(pageable);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET all students for dropdown or other views (no pagination)
    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.list(Pageable.unpaged()).getContent();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        Optional<Student> student = studentService.get(id);
        return student.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST - create a new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        // Ensure that required relationships (Class and ParentGuardian) are provided
        if (student.getAssignedClass() == null || student.getParentGuardian() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Bad request if relationships are not set
        }
        Student savedStudent = studentService.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    // PUT - update an existing student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student student) {
        Optional<Student> existingStudent = studentService.get(id);
        if (existingStudent.isPresent()) {
            // Convert the Long ID to String before setting
            student.setId(String.valueOf(id)); // Set the ID for the student to update
            if (student.getAssignedClass() == null || student.getParentGuardian() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Ensure relationships are set
            }
            Student updatedStudent = studentService.update(student);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Not found if the student doesn't exist
        }
    }

    // DELETE - delete a student by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        Optional<Student> student = studentService.get(id);
        if (student.isPresent()) {
            studentService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // No content on successful deletion
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Not found if student doesn't exist
        }
    }

    // GET all students with filtering and pagination (using Specification)
    @GetMapping("/filter")
    public ResponseEntity<Page<Student>> getFilteredStudents(Pageable pageable, Specification<Student> specification) {
        Page<Student> filteredStudents = studentService.list(pageable, specification);
        return new ResponseEntity<>(filteredStudents, HttpStatus.OK);
    }

    // GET count of all students
    @GetMapping("/count")
    public ResponseEntity<Integer> getStudentCount() {
        int count = studentService.count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    // GET all classes for dropdown in StudentView
    @GetMapping("/classes")
    public ResponseEntity<List<Class>> getAllClasses() {
        List<Class> classes = studentService.getAllClasses();
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }
}
