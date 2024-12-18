package com.admin.school.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Student {

    @Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(name = "custom-id-generator", strategy = "com.admin.school.IDGenerator.CustomPrefixIdGenerator")
    private String id;  // Primary Key

    private String firstName;
    private String lastName;
    private String sex;

    @Email
    private String email;

    private String phone;
    private LocalDate dateOfBirth;
    private String enrollmentStatus;
    private String medicalCondition;

    private Integer numberOfSiblings;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class assignedClass;

    @ManyToOne
    @JoinColumn(name = "parent_guardian_id")
    private ParentGuardian parentGuardian;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DisciplinaryRecord> disciplinaryRecords;

    @ManyToMany
    @JoinTable(
        name = "student_extracurricular",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<ExtracurricularActivity> extracurricularActivities;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private PerformanceAssessment performanceAssessment;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Health> healthRecords; // List of health records for the student

    private boolean isActive = true; // Field to track if the student is active or inactive

    // Relationship with Finance
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private Finance finance;  // This assumes the student has a one-to-one relationship with finance

    // Getters and Setters

    public void deactivate() {
        this.isActive = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    public Integer getNumberOfSiblings() {
        return numberOfSiblings;
    }

    public void setNumberOfSiblings(Integer numberOfSiblings) {
        this.numberOfSiblings = numberOfSiblings;
    }

    public Class getAssignedClass() {
        return assignedClass;
    }

    public void setAssignedClass(Class assignedClass) {
        this.assignedClass = assignedClass;
    }

    public ParentGuardian getParentGuardian() {
        return parentGuardian;
    }

    public void setParentGuardian(ParentGuardian parentGuardian) {
        this.parentGuardian = parentGuardian;
    }

    public List<DisciplinaryRecord> getDisciplinaryRecords() {
        return disciplinaryRecords;
    }

    public void setDisciplinaryRecords(List<DisciplinaryRecord> disciplinaryRecords) {
        this.disciplinaryRecords = disciplinaryRecords;
    }

    public List<ExtracurricularActivity> getExtracurricularActivities() {
        return extracurricularActivities;
    }

    public void setExtracurricularActivities(List<ExtracurricularActivity> extracurricularActivities) {
        this.extracurricularActivities = extracurricularActivities;
    }

    public PerformanceAssessment getPerformanceAssessment() {
        return performanceAssessment;
    }

    public void setPerformanceAssessment(PerformanceAssessment performanceAssessment) {
        this.performanceAssessment = performanceAssessment;
    }

    public List<Health> getHealthRecords() {
        return healthRecords;
    }

    public void setHealthRecords(List<Health> healthRecords) {
        this.healthRecords = healthRecords;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Finance getFinance() {
        return finance;
    }

    public void setFinance(Finance finance) {
        this.finance = finance;
    }
}
