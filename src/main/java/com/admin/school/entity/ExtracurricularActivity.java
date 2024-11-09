// src/main/java/com/management/school/entity/ExtracurricularActivity.java
package com.admin.school.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class ExtracurricularActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToMany(mappedBy = "extracurricularActivities")
    private List<Student> students;
    
    
    //Getter and Setter methods
    
    public String getName(){
    	return name;
    }
    public void setName(String name) {
    	this.name=name;
    }
    public String getDescription(){
    	return description;
    }
    public void setDescription(String description) {
    	this.description=description;
    }
}
