package com.admin.school.views.StudentsView;

import org.springframework.beans.factory.annotation.Autowired;
import com.admin.school.entity.ClassEntity;
import com.admin.school.entity.ParentGuardian;
import com.admin.school.entity.Student;
import com.admin.school.services.ParentGuardianService;
import com.admin.school.services.StudentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@PageTitle("Admit Student")
@Route("student")
@Menu(order = 6, icon = "line-awesome/svg/credit-card.svg")
@RolesAllowed("USER")
public class StudentView extends VerticalLayout {

    private final StudentService studentService;
    private final ParentGuardianService guardianService;

    // Inject dependencies
    @Autowired
    public StudentView(StudentService studentService, ParentGuardianService guardianService) {
        this.studentService = studentService;
        this.guardianService = guardianService;

        // Create the title
        H1 title = new H1("Student Details");

        // Create TextFields for Student Info (editable)
        TextField firstNameField = new TextField("First Name");
        TextField middleNameField = new TextField("Middle Name");
        TextField lastNameField = new TextField("Last Name");
        TextField otherNamesField = new TextField("Other Names");
        
        Select<String> sexField = new Select<>();
        sexField.setLabel("Sex");
        sexField.setItems("Male", "Female");
        
        DatePicker dateOfBirthField = new DatePicker("Date of Birth");
        NumberField numberOfSiblingsField = new NumberField("Number of Siblings");
        
        // Class entity (Assume we have a list of class names or IDs)
        Select<ClassEntity> classEntitySelect = new Select<>();
        classEntitySelect.setLabel("Class");
        // Assume you have a method in the service to fetch all classes
        // classEntitySelect.setItems(studentService.getAllClasses()); 
        
        // Display the student ID, but as read-only
        TextField idField = new TextField("Student ID");
        idField.setReadOnly(true); // The Student ID should be displayed but not editable

        // Button to save student details
        Button saveButton = new Button("Save");
        saveButton.addClickListener(e -> {
            // Collect data and save student
            Student student = new Student();
            student.setFirstName(firstNameField.getValue());
            student.setMiddleName(middleNameField.getValue());
            student.setLastName(lastNameField.getValue());
            student.setOtherNames(otherNamesField.getValue());
            student.setSex(sexField.getValue());
            student.setDateOfBirth(dateOfBirthField.getValue());
            student.setNumberOfSiblings(numberOfSiblingsField.getValue() != null ? numberOfSiblingsField.getValue().intValue() : 0);
            student.setClassEntity(classEntitySelect.getValue());

            studentService.saveStudent(student);

            Notification successNotification = Notification.show("Student saved successfully.");
            successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            successNotification.setPosition(Notification.Position.MIDDLE);
        });

        // TextFields for Guardian Info (For adding guardians)
        TextField guardianFirstName = new TextField("Guardian First Name");
        TextField guardianLastName = new TextField("Guardian Last Name");

        Button addGuardianButton = new Button("Add Guardian");

        addGuardianButton.addClickListener(e -> {
            String studentId = idField.getValue();

            if (studentId != null && !studentId.isEmpty()) {
                // Retrieve the Student from the database using the Student ID
                Student student = studentService.getStudentById(studentId).orElse(null);

                if (student != null) {
                    ParentGuardian newGuardian = new ParentGuardian();
                    newGuardian.setFirstName(guardianFirstName.getValue());
                    newGuardian.setLastName(guardianLastName.getValue());
                    guardianService.saveGuardian(newGuardian);

                    // Add the new guardian to the student's guardians list
                    student.addGuardian(newGuardian);
                    studentService.saveStudent(student);

                    // Clear the input fields for guardian
                    guardianFirstName.clear();
                    guardianLastName.clear();

                    Notification successNotification = Notification.show("Guardian added successfully.");
                    successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    successNotification.setPosition(Notification.Position.MIDDLE);
                } else {
                    Notification errorNotification = Notification.show("Student not found.");
                    errorNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    errorNotification.setPosition(Notification.Position.MIDDLE);
                }
            }
        });

        // Horizontal Layout for Student Information
        HorizontalLayout studentInfoLayout = new HorizontalLayout(idField, firstNameField, middleNameField, lastNameField, otherNamesField);
        HorizontalLayout studentAdditionalInfoLayout = new HorizontalLayout(sexField, dateOfBirthField, numberOfSiblingsField, classEntitySelect);
        
        // Horizontal Layout for Guardian Info
        HorizontalLayout guardianInfoLayout = new HorizontalLayout(guardianFirstName, guardianLastName);

        // Add all components to the layout
        add(title, studentInfoLayout, studentAdditionalInfoLayout, saveButton, guardianInfoLayout, addGuardianButton);
    }
}
