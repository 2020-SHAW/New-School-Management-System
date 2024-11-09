package com.admin.school.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.swing.text.Document;

@Entity
public class Student {

    @Id
    @GeneratedValue(generator = "student-id-gen")
    @GenericGenerator(
        name = "student-id-gen",
        strategy = "com.admin.school.IDGenerator.CustomPrefixIdGenerator"
    )
    private String id;  // Auto-generated ID with "ST" prefix
    
    private String firstName;
    private String middleName;
    private String lastName;
    private String otherNames;

    private String sex;
    private LocalDate dateOfBirth;
    private int numberOfSiblings;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassEntity classEntity;

    // One-to-many relationship with guardians
    @OneToMany(cascade = CascadeType.ALL)  // A student can have multiple guardians
    @JoinColumn(name = "student_id")  // This will reference the student ID in the ParentGuardian table
    private List<ParentGuardian> guardians;

    @ElementCollection
    private Set<byte[]> documentContents;

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

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
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

    public int getNumberOfSiblings() {
        return numberOfSiblings;
    }

    public void setNumberOfSiblings(int numberOfSiblings) {
        this.numberOfSiblings = numberOfSiblings;
    }

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public List<ParentGuardian> getGuardians() {
        return guardians;
    }

    public void setGuardians(List<ParentGuardian> guardians) {
        this.guardians = guardians;
    }

    public Set<Document> getDocuments() {
        return getDocuments();
    }

    public void setDocuments(Set<byte[]> documents) {
        this.documentContents = documents;
    }

    // You can add utility methods to add a guardian, if necessary
    public void addGuardian(ParentGuardian guardian) {
        this.guardians.add(guardian);
    }
}
