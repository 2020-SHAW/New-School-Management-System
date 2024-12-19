package com.admin.school.views.TeacherView;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.admin.school.entity.Teacher;
import com.admin.school.services.TeacherService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileData;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.Component;

@Route("teacher-admission")
@PageTitle("Teacher Admission")
@Menu(order = 19, icon = "line-awesome/svg/th-solid.svg")
@RolesAllowed("ADMIN")
public class TeacherAdmissionView extends VerticalLayout {

    private final TeacherService teacherService;

    // Form fields
    private TextField firstNameField;
    private TextField middleNameField;
    private TextField lastNameField;
    private TextField subjectSpecializationField;
    private TextField kraPinField;
    private TextField residenceField;
    private ComboBox<String> maritalStatusComboBox;
    private ComboBox<String> sexComboBox;
    private DatePicker dateOfBirthField;
    private Upload photoUpload;
    private Upload resumeUpload;

    // Dynamic fields for certifications
    private VerticalLayout certificationsLayout;
    private Button addCertificationButton;

    // Dynamic fields for kids
    private ComboBox<String> hasKidsComboBox;
    private TextField numberOfChildrenField;

    private Button submitButton;

    @Autowired
    public TeacherAdmissionView(TeacherService teacherService) {
        this.teacherService = teacherService;
        initUI();
    }

    private void initUI() {
        // Create fields for the form
        firstNameField = new TextField("First Name");
        middleNameField = new TextField("Middle Name");
        lastNameField = new TextField("Last Name");
        subjectSpecializationField = new TextField("Subject Specialization");
        kraPinField = new TextField("KRA PIN");
        residenceField = new TextField("Residence");

        maritalStatusComboBox = new ComboBox<>("Marital Status");
        maritalStatusComboBox.setItems("Single", "Married", "Divorced");

        sexComboBox = new ComboBox<>("Sex");
        sexComboBox.setItems("Male", "Female");

        dateOfBirthField = new DatePicker("Date of Birth");

        // Create the upload component for the teacher's photo
        MemoryBuffer photoBuffer = new MemoryBuffer();
        photoUpload = new Upload(photoBuffer);
        photoUpload.setAcceptedFileTypes("image/png", "image/jpeg");
        photoUpload.setMaxFiles(1);
        photoUpload.setDropLabel(new Div("Drag & drop your photo here or click to browse"));

        // Create the upload component for the resume
        MemoryBuffer resumeBuffer = new MemoryBuffer();
        resumeUpload = new Upload(resumeBuffer);
        resumeUpload.setAcceptedFileTypes("application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        resumeUpload.setMaxFiles(1);
        resumeUpload.setDropLabel(new Div("Drag & drop your resume here or click to browse"));

        // Create dynamic certifications input area
        certificationsLayout = new VerticalLayout();
        addCertificationButton = new Button("Add Certification", event -> addCertificationInput());
        certificationsLayout.add(createCertificationField()); // Initially one certification field
        certificationsLayout.add(addCertificationButton);

        // Dynamic field to ask about kids if marital status is "Married"
        hasKidsComboBox = new ComboBox<>("Do you have kids?");
        hasKidsComboBox.setItems("Yes", "No");
        hasKidsComboBox.setVisible(false); // Initially hidden
        hasKidsComboBox.addValueChangeListener(event -> onKidsSelectionChanged(event.getValue()));

        numberOfChildrenField = new TextField("Number of children");
        numberOfChildrenField.setVisible(false); // Initially hidden

        // Submit button
        submitButton = new Button("Submit", event -> submitTeacherData());

        // Layout the form
        FormLayout formLayout = new FormLayout(
            firstNameField, 
            middleNameField, 
            lastNameField, 
            subjectSpecializationField, 
            kraPinField, 
            residenceField, 
            maritalStatusComboBox, 
            sexComboBox, 
            dateOfBirthField, 
            photoUpload, 
            resumeUpload, 
            certificationsLayout, 
            hasKidsComboBox, 
            numberOfChildrenField, 
            submitButton
        );

        add(formLayout);

        // Listen for marital status changes to show the kids-related fields
        maritalStatusComboBox.addValueChangeListener(event -> onMaritalStatusChanged(event.getValue()));
    }

    private void onMaritalStatusChanged(String maritalStatus) {
        // Show the "Do you have kids?" field if the marital status is "Married"
        if ("Married".equals(maritalStatus)) {
            hasKidsComboBox.setVisible(true);
        } else {
            hasKidsComboBox.setVisible(false);
            numberOfChildrenField.setVisible(false); // Hide the number of children field
        }
    }

    private void onKidsSelectionChanged(String value) {
        // If the teacher has kids, show the number of children field
        if ("Yes".equals(value)) {
            numberOfChildrenField.setVisible(true);
        } else {
            numberOfChildrenField.setVisible(false);
        }
    }

    private Component createCertificationField() {
        TextField certificationField = new TextField("Certification");
        certificationField.setPlaceholder("Enter your certification");
        return certificationField;
    }

    private void addCertificationInput() {
        certificationsLayout.add(createCertificationField());
    }

    private void submitTeacherData() {
        // Validate the form fields before creating a teacher object
        if (firstNameField.isEmpty() || lastNameField.isEmpty() || subjectSpecializationField.isEmpty()) {
            UI.getCurrent().getPage().executeJs("alert('Please fill in the required fields!');");
            return;
        }

        // Create the Teacher object from form data
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstNameField.getValue());
        teacher.setMiddleName(middleNameField.getValue());
        teacher.setLastName(lastNameField.getValue());
        teacher.setSubjectSpecialization(subjectSpecializationField.getValue());
        teacher.setKraPin(kraPinField.getValue());
        teacher.setResidence(residenceField.getValue());
        teacher.setMaritalStatus(maritalStatusComboBox.getValue());
        teacher.setSex(sexComboBox.getValue());
        teacher.setDateOfBirth(dateOfBirthField.getValue());

        // Handle file uploads (photo and resume)
        if (photoUpload.getReceiver() != null) {
            MemoryBuffer photoBuffer = (MemoryBuffer) photoUpload.getReceiver();
            FileData photoData = photoBuffer.getFileData();
            teacher.setPhotoPath(saveFile(photoData, "photos"));
        }

        if (resumeUpload.getReceiver() != null) {
            MemoryBuffer resumeBuffer = (MemoryBuffer) resumeUpload.getReceiver();
            FileData resumeData = resumeBuffer.getFileData();
            teacher.setResumePath(saveFile(resumeData, "resumes"));
        }

        // Collect certifications
        teacher.setCertifications(collectCertifications());

        // Handle kids-related fields if marital status is "Married" and has kids
        if ("Married".equals(teacher.getMaritalStatus()) && "Yes".equals(hasKidsComboBox.getValue())) {
            teacher.setNumberOfChildren(Integer.parseInt(numberOfChildrenField.getValue()));
        }

        // Save the Teacher entity
        teacherService.saveTeacher(teacher);

        // Show success message and clear form
        UI.getCurrent().getPage().executeJs("alert('Teacher has been successfully added!');");
        clearForm();
    }

    private Set<String> collectCertifications() {
        Set<String> certifications = new HashSet<>();
        certificationsLayout.getChildren().forEach(component -> {
            if (component instanceof TextField) {
                String certification = ((TextField) component).getValue();
                if (!certification.isEmpty()) {
                    certifications.add(certification);  // Store as a string, not byte[]
                }
            }
        });
        return certifications;
    }


    private String saveFile(FileData photoData, String folder) {
        // This method will handle file storage, either saving to disk or a database
        // For now, let's assume you just return a placeholder string for the file path
        return folder + "/teacher_" + System.currentTimeMillis() + ".jpg";  // Sample file name
    }

    private void clearForm() {
        firstNameField.clear();
        middleNameField.clear();
        lastNameField.clear();
        subjectSpecializationField.clear();
        kraPinField.clear();
        residenceField.clear();
        maritalStatusComboBox.clear();
        sexComboBox.clear();
        dateOfBirthField.clear();
        certificationsLayout.removeAll(); // Remove all dynamic fields
        certificationsLayout.add(createCertificationField()); // Add the initial one back
        photoUpload.clearFileList();
        resumeUpload.clearFileList();
        hasKidsComboBox.clear();
        numberOfChildrenField.clear();
    }
}
