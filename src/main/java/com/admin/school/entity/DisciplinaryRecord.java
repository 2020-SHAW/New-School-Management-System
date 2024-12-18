package com.admin.school.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DisciplinaryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    private Decision finalDecision;

    private String rulesBroken;
    private String temporaryPunishment;
    private int suspensionWeeks;
    private LocalDate suspensionEndDate;

    // Enum for disciplinary decision
    public enum Decision {
        WARNING, 
        SUSPENSION, 
        EXPELLED
    }

    // Getter and Setter for 'id'
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for 'description'
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for 'date'
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Getter and Setter for 'student'
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    // Getter and Setter for 'teacher'
    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    // Getter and Setter for 'finalDecision'
    public Decision getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(Decision finalDecision) {
        this.finalDecision = finalDecision;
    }

    // Getter and Setter for 'rulesBroken'
    public String getRulesBroken() {
        return rulesBroken;
    }

    public void setRulesBroken(String rulesBroken) {
        this.rulesBroken = rulesBroken;
    }

    // Getter and Setter for 'temporaryPunishment'
    public String getTemporaryPunishment() {
        return temporaryPunishment;
    }

    public void setTemporaryPunishment(String temporaryPunishment) {
        this.temporaryPunishment = temporaryPunishment;
    }

    // Getter and Setter for 'suspensionWeeks'
    public int getSuspensionWeeks() {
        return suspensionWeeks;
    }

    public void setSuspensionWeeks(int suspensionWeeks) {
        this.suspensionWeeks = suspensionWeeks;
    }

    // Getter and Setter for 'suspensionEndDate'
    public LocalDate getSuspensionEndDate() {
        return suspensionEndDate;
    }

    public void setSuspensionEndDate(LocalDate suspensionEndDate) {
        this.suspensionEndDate = suspensionEndDate;
    }
}
