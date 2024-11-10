// src/main/java/com/admin/school/controller/TeacherController.java
package com.admin.school.controller;

import com.admin.school.entity.Teacher;
import com.admin.school.services.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // GET all teachers with pagination
    @GetMapping
    public ResponseEntity<Page<Teacher>> getAllTeachers(Pageable pageable) {
        Page<Teacher> teachers = teacherService.list(pageable);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // GET teacher by ID
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        Optional<Teacher> teacher = teacherService.get(id);
        return teacher.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST - create a new teacher
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Teacher savedTeacher = teacherService.update(teacher);  // Using update here because save is similar in this case
        return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
    }

    // PUT - update an existing teacher
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher teacher) {
        // Ensure the teacher exists before updating
        Optional<Teacher> existingTeacher = teacherService.get(id);
        if (existingTeacher.isPresent()) {
            teacher.setId(id); // Ensure the ID is set for updating
            Teacher updatedTeacher = teacherService.update(teacher);
            return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - delete a teacher by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        Optional<Teacher> teacher = teacherService.get(id);
        if (teacher.isPresent()) {
            teacherService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET all teachers with filtering and pagination (using Specification)
    @GetMapping("/filter")
    public ResponseEntity<Page<Teacher>> getFilteredTeachers(Pageable pageable, Specification<Teacher> specification) {
        Page<Teacher> filteredTeachers = teacherService.list(pageable, specification);
        return new ResponseEntity<>(filteredTeachers, HttpStatus.OK);
    }

    // GET count of all teachers
    @GetMapping("/count")
    public ResponseEntity<Integer> getTeacherCount() {
        int count = teacherService.count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
