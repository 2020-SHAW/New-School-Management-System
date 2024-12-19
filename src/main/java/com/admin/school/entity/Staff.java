package com.admin.school.entity;

import com.admin.school.IDGenerator.CustomPrefixIdGenerator;
import com.admin.school.data.Role;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Entity
public class Staff {

    @Id
    @GeneratedValue(generator = "custom-prefix-id-generator")
    @GenericGenerator(
            name = "custom-prefix-id-generator",
            strategy = "com.admin.school.IDGenerator.CustomPrefixIdGenerator"
    )
    private String id;

    private String firstName;
    private String middleName;
    private String lastName;
    private String position;
    private String kraPin;
    private String residence;
    
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    
    @Enumerated(EnumType.STRING)
    private Sex sex;
    
    private LocalDate dateOfBirth;
    private String photoPath;
    private String resumePath;
    private int numberOfChildren;
    
    @ElementCollection
    private Set<String> certifications;
    
    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private boolean isPresent;

    // Method to calculate age based on the dateOfBirth
    public String getAge() {
        if (dateOfBirth == null) {
            return "Unknown";  // Handle case where dateOfBirth is not set
        }

        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);
        
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();

        return years + " years, " + months + " months, " + days + " days";
    }

    // Getters and Setters
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public Set<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(Set<String> certifications) {
        this.certifications = certifications;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

    // Enum types for marital status and sex
    public enum MaritalStatus {
        SINGLE, MARRIED, DIVORCED
    }

    public enum Sex {
        MALE, FEMALE
    }
}
