// src/main/java/com/management/school/data/PerformanceAssessment.java
package com.admin.school.entity;

import jakarta.persistence.*;

@Entity
public class PerformanceAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assessmentDetails;
    private double score;

    @ManyToOne // Foreign key to Teacher
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne // Foreign key to Student
    @JoinColumn(name = "student_id")
    private Student student;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssessmentDetails() {
        return assessmentDetails;
    }

    public void setAssessmentDetails(String assessmentDetails) {
        this.assessmentDetails = assessmentDetails;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
