package com.admin.school.views.HealthView;

import com.admin.school.entity.Health;
import com.admin.school.entity.Student;
import com.admin.school.entity.Teacher;
import com.admin.school.repository.HealthRepository;
import com.admin.school.repository.StudentRepository;
import com.admin.school.repository.TeacherRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.time.ZoneId;

@Route("health")
@Menu(order = 14, icon = "line-awesome/svg/th-solid.svg")
@RolesAllowed("USER")
public class HealthView extends VerticalLayout {

    private final HealthRepository healthRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    private TextField idField;
    private Button searchButton;
    private FormLayout healthForm;
    private Grid<Health> healthGrid;
    private TextArea symptomsField;
    private TextArea previousIllnessesField;
    private TextArea prescriptionsField;
    private DatePicker recordDatePicker;
    private Button saveRecordButton;

    @Autowired
    public HealthView(HealthRepository healthRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.healthRepository = healthRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;

        createUI();
    }

    private void createUI() {
        // ID input field for searching a student or teacher
        idField = new TextField("Enter ID (Student or Teacher)");
        idField.setPlaceholder("Student ID or Teacher ID");

        searchButton = new Button("Search", event -> searchHealthRecord());

        // Health record form fields
        healthForm = new FormLayout();
        symptomsField = new TextArea("Symptoms");
        previousIllnessesField = new TextArea("Previous Illnesses");
        prescriptionsField = new TextArea("Prescriptions");
        recordDatePicker = new DatePicker("Record Date");

        saveRecordButton = new Button("Save Health Record", event -> saveHealthRecord());

        // Grid to show existing health records
        healthGrid = new Grid<>(Health.class);
        healthGrid.setColumns("symptoms", "previousIllnesses", "prescriptions", "recordDate");

        // Layout
        add(idField, searchButton, healthForm, saveRecordButton, healthGrid);
    }

    private void searchHealthRecord() {
        String id = idField.getValue();
        if (id.isEmpty()) {
            Notification.show("Please enter a valid ID.");
            return;
        }

        // Check if the ID belongs to a student or teacher
        if (isStudentId(id)) {
            Student student = studentRepository.findById(id).orElse(null);
            if (student != null) {
                populateHealthForm(student);
            } else {
                Notification.show("Student not found.");
            }
        } else if (isTeacherId(id)) {
            Teacher teacher = teacherRepository.findById(id).orElse(null);
            if (teacher != null) {
                populateHealthForm(teacher);
            } else {
                Notification.show("Teacher not found.");
            }
        } else {
            Notification.show("Invalid ID format.");
        }
    }

    private void populateHealthForm(Student student) {
        // Populate the health records for the student
        healthForm.setVisible(true);
        healthGrid.setItems(healthRepository.findByStudentId(student.getId()));
    }

    private void populateHealthForm(Teacher teacher) {
        // Populate the health records for the teacher
        healthForm.setVisible(true);
        healthGrid.setItems(healthRepository.findByTeacherId(teacher.getId()));
    }

    private void saveHealthRecord() {
        String id = idField.getValue();
        if (id.isEmpty()) {
            Notification.show("Please enter a valid ID.");
            return;
        }

        // Create and populate the Health entity
        Health health = new Health();
        health.setSymptoms(symptomsField.getValue());
        health.setPreviousIllnesses(previousIllnessesField.getValue());
        health.setPrescriptions(prescriptionsField.getValue());

        // Automatically set the current date and time for Nairobi (Kenya) if not selected manually
        if (recordDatePicker.getValue() == null) {
            // Use current date in Nairobi timezone (GMT+3)
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Africa/Nairobi"));
            health.setRecordDate(now.toLocalDate());  // Set current date
        } else {
            health.setRecordDate(recordDatePicker.getValue());  // Use manually selected date
        }

        // Save record based on student or teacher ID
        if (isStudentId(id)) {
            Student student = studentRepository.findById(id).orElse(null);
            if (student != null) {
                health.setStudent(student);
                healthRepository.save(health);
                Notification.show("Health record saved.");
                healthGrid.setItems(healthRepository.findByStudentId(student.getId())); // Refresh grid
            } else {
                Notification.show("Student not found.");
            }
        } else if (isTeacherId(id)) {
            Teacher teacher = teacherRepository.findById(id).orElse(null);
            if (teacher != null) {
                health.setTeacher(teacher);
                healthRepository.save(health);
                Notification.show("Health record saved.");
                healthGrid.setItems(healthRepository.findByTeacherId(teacher.getId())); // Refresh grid
            } else {
                Notification.show("Teacher not found.");
            }
        }
    }

    private boolean isStudentId(String id) {
        // Check if the ID is for a student
        return id.startsWith("ST");
    }

    private boolean isTeacherId(String id) {
        // Check if the ID is for a teacher
        return id.startsWith("TC");
    }
}
