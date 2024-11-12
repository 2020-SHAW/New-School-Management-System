package com.admin.school.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

import java.util.List;

@Entity
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the primary key
    private Long id;

    private String name;
    private String grade;

    private Boolean isActive; // New field to track if the class is active

    // One-to-many relationship with Student. A class can have many students
    @OneToMany(mappedBy = "assignedClass", fetch = FetchType.EAGER) // 'assignedClass' will be the reference in the Student entity
    private List<Student> students;

    // Many-to-one relationship with Teacher. A class has one teacher (but it's now optional)
    @ManyToOne(fetch = FetchType.LAZY, optional = true) // Class can exist without a teacher
    @JoinColumn(name = "class_teacher_id") // Column to store the teacher's ID
    private Teacher classTeacher;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Boolean getIsActive() {  // Getter for the isActive field
        return isActive;
    }

    public void setIsActive(Boolean isActive) {  // Setter for the isActive field
        this.isActive = isActive;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Teacher getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(Teacher classTeacher) {
        this.classTeacher = classTeacher;
    }
}
