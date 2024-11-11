package com.admin.school.entity;

import com.admin.school.IDGenerator.CustomPrefixIdGenerator;
import jakarta.persistence.*;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class ParentGuardian {

    @Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(name = "custom-id-generator", strategy = "com.admin.school.IDGenerator.CustomPrefixIdGenerator")
    private String id;  // Primary Key

    private String firstName;
    private String lastName;
    private String relationship;
    private String phoneNumber;
    private String email;
    private String address;

    @OneToMany(mappedBy = "parentGuardian")
    private List<Student> students;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
