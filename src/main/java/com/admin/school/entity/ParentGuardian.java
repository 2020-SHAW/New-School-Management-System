// src/main/java/com/management/school/entity/ParentGuardian.java
package com.admin.school.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;

import com.admin.school.data.AbstractEntity;

@Entity
public class ParentGuardian extends AbstractEntity {

    private String name;
    private String relationship;
    private String phone;
    private String address;

    @OneToMany(mappedBy = "parentGuardian")
    private List<Student> students;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRelationship() { return relationship; }
    public void setRelationship(String relationship) { this.relationship = relationship; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}
