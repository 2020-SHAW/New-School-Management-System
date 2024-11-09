// src/main/java/com/management/school/entity/CourseSubject.java
package com.admin.school.entity;

import jakarta.persistence.*;

@Entity
public class CourseSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subjectName;

    // Getters and Setters
    public String getSubjectName() {
    	return subjectName;
    }
    public void setSubjectName(String subjectName) {
    	this.subjectName=subjectName;
    }
}
