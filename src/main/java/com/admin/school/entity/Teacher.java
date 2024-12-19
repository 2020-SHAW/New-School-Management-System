package com.admin.school.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(generator = "teacher-id-gen")
    @GenericGenerator(
        name = "teacher-id-gen",
        strategy = "com.admin.school.IDGenerator.CustomPrefixIdGenerator"
    )
    private String id; // Auto-generated ID with "TC" prefix

    @NotNull
    @Size(min = 1, max = 100)
    private String firstName;

    private String middleName;

    @NotNull
    @Size(min = 1, max = 100)
    private String lastName;

    private String subjectSpecialization;

    private String kraPin;

    private String residence;

    private String maritalStatus;

    private String sex;

    private LocalDate dateOfBirth;

    @ElementCollection
    @CollectionTable(name = "teacher_certifications", joinColumns = @JoinColumn(name = "teacher_id"))
    @Column(name = "certification")
    private Set<String> certifications; // Store certifications as String

    private String photoPath; // Path to image file (e.g., PNG, JPG)
    private String resumePath; // Path to resume file (e.g., DOCX, PDF)

    // One-to-many relationship with health records
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Health> healthRecords; // List of health records for the teacher

    private boolean isBlacklisted = false; // Field to track if the teacher is blacklisted

    private Integer numberOfChildren; // Number of children (only if maritalStatus is "Married")
    private boolean hasKids; // Whether the teacher has kids (only if maritalStatus is "Married")

    // Method to blacklist the teacher upon expulsion
    public void blacklist() {
        this.isBlacklisted = true;
    }

    // Getters and setters

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSubjectSpecialization() {
        return subjectSpecialization;
    }

    public void setSubjectSpecialization(String subjectSpecialization) {
        this.subjectSpecialization = subjectSpecialization;
    }

    public String getKraPin() {
        return kraPin;
    }

    public void setKraPin(String kraPin) {
        this.kraPin = kraPin;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Set<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(Set<String> certifications) {
        this.certifications = certifications;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getResumePath() {
        return resumePath;
    }

    public void setResumePath(String resumePath) {
        this.resumePath = resumePath;
    }

    public List<Health> getHealthRecords() {
        return healthRecords;
    }

    public void setHealthRecords(List<Health> healthRecords) {
        this.healthRecords = healthRecords;
    }

    public boolean isBlacklisted() {
        return isBlacklisted;
    }

    public void setBlacklisted(boolean blacklisted) {
        isBlacklisted = blacklisted;
    }

    public Integer getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(Integer numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public boolean isHasKids() {
        return hasKids;
    }

    public void setHasKids(boolean hasKids) {
        this.hasKids = hasKids;
    }
}
