package com.admin.school.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.admin.school.entity.ParentGuardian;
import com.admin.school.entity.Student;
import com.admin.school.services.ParentGuardianService;
import com.admin.school.services.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final ParentGuardianService guardianService;

    @Autowired
    public StudentController(StudentService studentService, ParentGuardianService guardianService) {
        this.studentService = studentService;
        this.guardianService = guardianService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @GetMapping("/{id}")
    public Optional<Student> getStudentById(@PathVariable("id") String studentId) {
        return studentService.getStudentById(studentId);
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/{studentId}/guardians")
    public void addGuardianToStudent(@PathVariable("studentId") String studentId, @RequestBody ParentGuardian guardian) {
        studentService.addGuardianToStudent(studentId, guardian);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") String studentId) {
        studentService.deleteStudent(studentId);
    }
}
