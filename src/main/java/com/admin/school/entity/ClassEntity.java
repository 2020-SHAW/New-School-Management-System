package com.admin.school.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class ClassEntity {

    @Id
    private String id;
    private String className;
    
    // Class Teacher: One teacher can be assigned as the class teacher
    @ManyToOne
    @JoinColumn(name = "class_teacher_id") // Optional: Can specify column name
    private Teacher classTeacher;

    // Subject Teachers: A class can have multiple teachers for different subjects
    @ManyToMany
    @JoinTable(
      name = "class_subject_teachers", 
      joinColumns = @JoinColumn(name = "class_id"), 
      inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> subjectTeachers;

    // Students: A class can have many students
    @OneToMany(mappedBy = "classEntity")
    private Set<Student> students;

    // Number of students in the class - this is calculated dynamically
    @Transient
    private int numberOfStudents;

    // Constructor, getters, setters, and other methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Teacher getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(Teacher classTeacher) {
        this.classTeacher = classTeacher;
    }

    public Set<Teacher> getSubjectTeachers() {
        return subjectTeachers;
    }

    public void setSubjectTeachers(Set<Teacher> subjectTeachers) {
        this.subjectTeachers = subjectTeachers;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public int getNumberOfStudents() {
        return students != null ? students.size() : 0;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }
}
