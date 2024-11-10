// src/main/java/com/management/school/entity/DisciplinaryRecord.java
package com.admin.school.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DisciplinaryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String description;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Getter and Setter for 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for 'description'
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for 'date'
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Getter and Setter for 'student'
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
