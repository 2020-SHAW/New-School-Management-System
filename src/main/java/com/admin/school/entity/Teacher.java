package com.admin.school.entity;

import java.time.LocalDate;
import java.util.Set;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Column;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
    @Lob
    @Column(name = "certification")
    private Set<byte[]> certifications; // Store certifications as byte arrays

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

    public String getFullName() {
        return firstName + " " + (middleName != null ? middleName + " " : "") + lastName;
    }
}
