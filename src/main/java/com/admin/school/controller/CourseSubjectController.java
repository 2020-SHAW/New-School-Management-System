// src/main/java/com/admin/school/controller/CourseSubjectController.java
package com.admin.school.controller;

import com.admin.school.entity.CourseSubject;
import com.admin.school.services.CourseSubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/courseSubjects")
public class CourseSubjectController {

    private final CourseSubjectService courseSubjectService;

    public CourseSubjectController(CourseSubjectService courseSubjectService) {
        this.courseSubjectService = courseSubjectService;
    }

    // GET course subject by ID
    @GetMapping("/{id}")
    public ResponseEntity<CourseSubject> getCourseSubjectById(@PathVariable Long id) {
        Optional<CourseSubject> courseSubject = courseSubjectService.get(id);
        return courseSubject.map(ResponseEntity::ok)
                            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST - create a new course subject
    @PostMapping
    public ResponseEntity<CourseSubject> createCourseSubject(@RequestBody CourseSubject courseSubject) {
        CourseSubject savedCourseSubject = courseSubjectService.save(courseSubject);
        return new ResponseEntity<>(savedCourseSubject, HttpStatus.CREATED);
    }

    // DELETE - delete a course subject by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseSubject(@PathVariable Long id) {
        Optional<CourseSubject> courseSubject = courseSubjectService.get(id);
        if (courseSubject.isPresent()) {
            courseSubjectService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
