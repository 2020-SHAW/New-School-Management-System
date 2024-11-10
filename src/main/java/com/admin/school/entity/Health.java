// src/main/java/com/management/school/data/Health.java
package com.admin.school.entity;

import jakarta.persistence.*;

@Entity
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	private String condition;

    @ManyToOne // Foreign key to Student
    @JoinColumn(name = "student_id")
    private Student student;
    
    // Getters and Setters
    public String getCondition() {
    	return condition;
    }
    public void setCondition(String condition) {
    	this.condition=condition;
    }
}
