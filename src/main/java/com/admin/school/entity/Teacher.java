package com.admin.school.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(generator = "teacher-id-gen")
    @GenericGenerator(
        name = "teacher-id-gen",
        strategy = "com.management.school.CustomGenerator.CustomPrefixIdGenerator"
    )
    private String id; // Auto-generated ID with "TC" prefix

    private String firstName;
    private String middleName;
    private String lastName;
    private String subjectSpecialization;
    private String kraPin;
    private String residence;
    private String maritalStatus;
    private String sex;
    private LocalDate dateOfBirth;

    @ElementCollection
    private Set<byte[]> certifications;

    // Store file paths or URLs for files on disk
    private String photoPath; // Path to image file (e.g., PNG, JPG)
    private String resumePath; // Path to resume file (e.g., DOCX, PDF)

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

    public Set<byte[]> getCertifications() {
        return certifications;
    }

    public void setCertifications(Set<byte[]> certifications) {
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
}
