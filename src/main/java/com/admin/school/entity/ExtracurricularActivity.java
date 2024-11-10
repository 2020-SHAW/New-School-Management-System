// src/main/java/com/management/school/entity/ExtracurricularActivity.java
package com.admin.school.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class ExtracurricularActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates the ID
    private Long id;

    private String name;
    private String description;

    @ManyToMany(mappedBy = "extracurricularActivities")
    private List<Student> students;

    // Getter and Setter methods (Do not add setId() method)

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

	public void setId(Long id) {
		this.id = id;
	}
}
