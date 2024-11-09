// src/main/java/com/management/school/entity/Teacher.java
package com.admin.school.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

import com.admin.school.data.AbstractEntity;

@Entity
public class Teacher extends AbstractEntity {

    private String name;
    private String subjectSpecialization;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "teacher")
    private List<PerformanceAssessment> performanceAssessments;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSubjectSpecialization() { return subjectSpecialization; }
    public void setSubjectSpecialization(String subjectSpecialization) { this.subjectSpecialization = subjectSpecialization; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<PerformanceAssessment> getPerformanceAssessments() { return performanceAssessments; }
    public void setPerformanceAssessments(List<PerformanceAssessment> performanceAssessments) { this.performanceAssessments = performanceAssessments; }
}
