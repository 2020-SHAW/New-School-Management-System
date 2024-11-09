package com.admin.school.views.StudentsView;

import com.admin.school.entity.Student;
import com.admin.school.services.ClassService;
import com.admin.school.services.StudentService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;  // Import ComboBox
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@PageTitle("Student Admission")
@Menu(order = 12, icon = "line-awesome/svg/th-solid.svg")
@RolesAllowed("USER")
@Route("student")
public class StudentView extends VerticalLayout {

    private final StudentService studentService;
    private final ClassService classService;
    private final Binder<Student> binder = new Binder<>(Student.class);

    private final TextField firstName = new TextField("First Name");
    private final TextField middleName = new TextField("Middle Name");
    private final TextField lastName = new TextField("Last Name");

    // Use ComboBox for sex
    private final ComboBox<String> sex = new ComboBox<>("Sex");
    
    private final DatePicker dateOfBirth = new DatePicker("Date of Birth");

    // Use NumberField with Integer type for number of siblings
    private final NumberField numberOfSiblings = new NumberField("Number of Siblings");

    private final Upload birthCertificateUpload;
    private final Upload medicalReportUpload;
    private final Upload resultSlipUpload;

    private final Button saveButton = new Button("Save", event -> saveStudent());

    private final Grid<Student> grid = new Grid<>(Student.class);

    public StudentView(StudentService studentService, ClassService classService) {
        this.studentService = studentService;
        this.classService = classService;

        // Initialize uploads
        birthCertificateUpload = createDocumentUpload("Birth Certificate");
        medicalReportUpload = createDocumentUpload("Medical Report");
        resultSlipUpload = createDocumentUpload("Result Slip");

        configureForm();
        configureGrid();

        add(new HorizontalLayout(firstName, middleName, lastName, sex, dateOfBirth, numberOfSiblings),
                birthCertificateUpload, medicalReportUpload, resultSlipUpload, saveButton, grid);
    }

    private Upload createDocumentUpload(String label) {
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("application/pdf");
        upload.setMaxFiles(1);
        upload.setUploadButton(new Button("Upload " + label));
        return upload;
    }

    private void configureForm() {
        // Configure the ComboBox for sex with two options
        sex.setItems("Male", "Female");  // Set available options
        sex.setRequired(true);  // Make it a required field

        // Bind fields directly without any converter since numberOfSiblings is an int
        binder.forField(numberOfSiblings)
            .withConverter(
                value -> value != null ? value.intValue() : 0,  // If null, return 0
                value -> (double) value)  // Convert int to double for NumberField
            .bind(Student::getNumberOfSiblings, Student::setNumberOfSiblings);

        // Automatically bind other fields
        binder.bindInstanceFields(this);
        binder.setBean(new Student()); // Bind to a new Student bean
    }

    private void configureGrid() {
        grid.setColumns("id", "firstName", "lastName", "sex", "dateOfBirth");
        grid.setItems(studentService.getAllStudents());
        grid.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));
    }

    private void populateForm(Student student) {
        binder.setBean(student != null ? student : new Student());
    }

    private void saveStudent() {
        Student student = binder.getBean();
        studentService.saveStudent(student);
        Notification.show("Student saved successfully!");
        refreshGrid();
    }

    private void refreshGrid() {
        grid.setItems(studentService.getAllStudents());
    }
}
