package com.admin.school.controller;

import com.admin.school.entity.Teacher;
import com.admin.school.services.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    // Constructor-based injection for TeacherService
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    // GET all teachers (pagination handled by Pageable)
    @GetMapping
    public ResponseEntity<Page<Teacher>> getAllTeachers(Pageable pageable) {
        Page<Teacher> teachers = teacherService.listTeachers(pageable); // Using pageable
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    // GET teacher by ID (ID is a String, as it is custom-generated)
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable String id) {
        Optional<Teacher> teacher = teacherService.getTeacherById(id);
        return teacher.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST - create a new teacher
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) {
        Teacher savedTeacher = teacherService.createOrUpdateTeacher(teacher);  // Using createOrUpdate here as save logic
        return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
    }

    // PUT - update an existing teacher
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable String id, @RequestBody Teacher teacher) {
        Optional<Teacher> existingTeacher = teacherService.getTeacherById(id);
        if (existingTeacher.isPresent()) {
            teacher.setId(id);  // Ensure the ID is set correctly for updating
            Teacher updatedTeacher = teacherService.createOrUpdateTeacher(teacher);
            return new ResponseEntity<>(updatedTeacher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE - delete a teacher by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable String id) {
        Optional<Teacher> teacher = teacherService.getTeacherById(id);
        if (teacher.isPresent()) {
            teacherService.deleteTeacherById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // GET count of all teachers
    @GetMapping("/count")
    public ResponseEntity<Long> getTeacherCount() {
        long count = teacherService.getTeacherCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
