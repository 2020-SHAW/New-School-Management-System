package com.admin.school.views.ParentGuardianView;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.admin.school.entity.ParentGuardian;
import com.admin.school.entity.Student;
import com.admin.school.services.ParentGuardianService;
import com.admin.school.services.StudentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@PageTitle("Parent and Guardian")
@Menu(order = 13, icon = "line-awesome/svg/th-solid.svg")
@RolesAllowed("USER")
@Route("guardian")
public class ParentGuardianView extends VerticalLayout {

    private final ParentGuardianService parentGuardianService;
    private final StudentService studentService;

    private TextField studentIdField;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField phoneField;
    private TextField emailField;
    private ComboBox<String> relationshipComboBox;
    private TextField customRelationshipField;

    private Student selectedStudent;

    // Grid to display both student and guardian details
    private Grid<Student> studentGrid;

    @Autowired
    public ParentGuardianView(ParentGuardianService parentGuardianService, StudentService studentService) {
        this.parentGuardianService = parentGuardianService;
        this.studentService = studentService;

        // Initialize components
        studentIdField = new TextField("Enter Student ID");
        firstNameField = new TextField("Guardian First Name");
        lastNameField = new TextField("Guardian Last Name");
        phoneField = new TextField("Guardian Phone Number");
        emailField = new TextField("Guardian Email");
        relationshipComboBox = new ComboBox<>("Relationship", "Mother", "Father", "Other");
        customRelationshipField = new TextField("Custom Relationship");

        customRelationshipField.setVisible(false);  // Initially hidden

        Button saveButton = new Button("Save Guardian", event -> saveGuardian());

        // Create Grid for displaying student and guardian details
        studentGrid = new Grid<>();
        studentGrid.addColumn(Student::getId).setHeader("Student ID");
        studentGrid.addColumn(student -> student.getFirstName() + " " + student.getLastName()).setHeader("Student Name");
        studentGrid.addColumn(student -> student.getPhone()).setHeader("Student Phone");
        studentGrid.addColumn(student -> student.getEmail()).setHeader("Student Email");

        studentGrid.addColumn(student -> {
            ParentGuardian guardian = student.getParentGuardian();
            return guardian != null ? guardian.getFirstName() + " " + guardian.getLastName() : "No Guardian";
        }).setHeader("Guardian Name");

        studentGrid.addColumn(student -> {
            ParentGuardian guardian = student.getParentGuardian();
            return guardian != null ? guardian.getRelationship() : "N/A";
        }).setHeader("Relationship");

        studentGrid.setHeight("200px");  // Set a fixed height for the grid

        // Add components to layout
        add(studentIdField, studentGrid, firstNameField, lastNameField, phoneField, emailField, relationshipComboBox, customRelationshipField, saveButton);

        // Bind events
        studentIdField.addValueChangeListener(e -> searchStudentById(e.getValue()));
        studentIdField.setValueChangeMode(com.vaadin.flow.data.value.ValueChangeMode.EAGER); // Trigger search as the user types
        relationshipComboBox.addValueChangeListener(e -> handleRelationshipChange(e.getValue()));
    }

    // Real-time search for student by ID
    private void searchStudentById(String studentId) {
        if (studentId != null && !studentId.isEmpty()) {
            try {
                Long id = Long.parseLong(studentId);  // Try to parse the ID
                studentService.get(id).ifPresentOrElse(student -> {
                    selectedStudent = student;
                    displayStudentDetails(student);
                    studentGrid.setItems(List.of(student));  // Display the student in the grid
                }, () -> {
                    // If student is not found
                    selectedStudent = null;
                    clearStudentDetails();
                    studentGrid.setItems();  // Clear the grid
                    Notification.show("Student not found.");
                });
            } catch (NumberFormatException e) {
                // Invalid student ID format, clear details and grid
                selectedStudent = null;
                clearStudentDetails();
                studentGrid.setItems();  // Clear the grid
                Notification.show("Invalid student ID format.");
            }
        } else {
            clearStudentDetails();
            studentGrid.setItems();  // Clear the grid when no student ID is entered
        }
    }

    // Display student details in the text fields (for guardian data)
    private void displayStudentDetails(Student student) {
        firstNameField.setValue("");  // Clear existing guardian details
        lastNameField.setValue("");
        phoneField.setValue("");
        emailField.setValue("");
    }

    // Clear student details when no student is found or input is invalid
    private void clearStudentDetails() {
        firstNameField.clear();
        lastNameField.clear();
        phoneField.clear();
        emailField.clear();
    }

    // Handle the selection of relationship type
    private void handleRelationshipChange(String relationship) {
        if ("Other".equals(relationship)) {
            customRelationshipField.setVisible(true);
        } else {
            customRelationshipField.setVisible(false);
            customRelationshipField.clear();
        }
    }

    // Save the guardian details
    private void saveGuardian() {
        if (selectedStudent == null) {
            Notification.show("Please select a valid student first.");
            return;
        }

        // Get the values from the form
        String firstName = firstNameField.getValue();
        String lastName = lastNameField.getValue();
        String phone = phoneField.getValue();
        String email = emailField.getValue();
        String relationship = relationshipComboBox.getValue();
        String customRelationship = customRelationshipField.getValue();

        if ("Other".equals(relationship) && customRelationship.isEmpty()) {
            Notification.show("Please specify the custom relationship.");
            return;
        }

        // Create the ParentGuardian entity
        ParentGuardian parentGuardian = new ParentGuardian();
        parentGuardian.setFirstName(firstName);
        parentGuardian.setLastName(lastName);
        parentGuardian.setPhoneNumber(phone);
        parentGuardian.setEmail(email);
        parentGuardian.setRelationship("Other".equals(relationship) ? customRelationship : relationship);

        // Save the parent guardian
        parentGuardianService.save(parentGuardian);

        // Associate guardian with the selected student
        selectedStudent.setParentGuardian(parentGuardian);
        studentService.save(selectedStudent);

        // Refresh the grid with the new guardian details
        studentGrid.getDataProvider().refreshAll();  // Refresh the grid to show updated guardian details

        Notification.show("Guardian saved successfully.");
    }
}
