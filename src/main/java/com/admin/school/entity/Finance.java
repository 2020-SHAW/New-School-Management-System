// src/main/java/com/management/school/data/Finance.java
package com.admin.school.entity;

import jakarta.persistence.*;

@Entity
public class Finance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amountDue;
    
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
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public double getAmountDue() {
    	return amountDue;
    }
    public void setAmountDue(double amountDue) {
    	this.amountDue=amountDue;
    }
}
