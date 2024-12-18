package com.admin.school.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Health {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bloodType;
    private String allergies;
    private String chronicConditions;
    private String medicalHistory; // Additional field for any medical history

    private String symptoms; // New field
    private String previousIllnesses; // New field
    private String prescriptions; // New field
    private LocalDate recordDate; // New field for the health record date

    // Many-to-one relationship with Student (One student can have multiple health records)
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student; // Reference to Student

    // Many-to-one relationship with Teacher (One teacher can have multiple health records)
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher; // Reference to Teacher

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getChronicConditions() {
        return chronicConditions;
    }

    public void setChronicConditions(String chronicConditions) {
        this.chronicConditions = chronicConditions;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPreviousIllnesses() {
        return previousIllnesses;
    }

    public void setPreviousIllnesses(String previousIllnesses) {
        this.previousIllnesses = previousIllnesses;
    }

    public String getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(String prescriptions) {
        this.prescriptions = prescriptions;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
