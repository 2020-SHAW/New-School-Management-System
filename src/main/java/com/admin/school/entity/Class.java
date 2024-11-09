// src/main/java/com/management/school/entity/Class.java
package com.admin.school.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.List;

import com.admin.school.data.AbstractEntity;

@Entity
public class Class extends AbstractEntity {

    private String name;
    private String grade;

    @ManyToMany(mappedBy = "assignedClass")
    private List<Student> students;

    // Getters and Setters
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
}
