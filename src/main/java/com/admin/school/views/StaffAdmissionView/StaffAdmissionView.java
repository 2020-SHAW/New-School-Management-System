package com.admin.school.views.StaffAdmissionView;

import java.util.HashSet;
import java.util.Set;

import com.admin.school.data.Role;
import com.admin.school.entity.Staff;
import com.admin.school.services.StaffService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;

@Route("staff-admission")
public class StaffAdmissionView extends VerticalLayout {

    private final StaffService staffService;

    // Form fields
    private TextField firstNameField;
    private TextField middleNameField;
    private TextField lastNameField;
    private TextField positionField;
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

    // MultiSelect ComboBox for roles
    private MultiSelectComboBox<Role> rolesComboBox;

    private Button submitButton;

    public StaffAdmissionView(StaffService staffService) {
        this.staffService = staffService;
        initUI();
    }

    private void initUI() {
        // Create fields for the form
        firstNameField = new TextField("First Name");
        middleNameField = new TextField("Middle Name");
        lastNameField = new TextField("Last Name");
        positionField = new TextField("Position");
        kraPinField = new TextField("KRA PIN");
        residenceField = new TextField("Residence");

        maritalStatusComboBox = new ComboBox<>("Marital Status");
        maritalStatusComboBox.setItems("Single", "Married", "Divorced");

        sexComboBox = new ComboBox<>("Sex");
        sexComboBox.setItems("Male", "Female");

        dateOfBirthField = new DatePicker("Date of Birth");

        // Create the upload component for the staff's photo
        MemoryBuffer photoBuffer = new MemoryBuffer();
        photoUpload = new Upload(photoBuffer);
        photoUpload.setAcceptedFileTypes("image/png", "image/jpeg");
        photoUpload.setMaxFiles(1);

        // Create the upload component for the resume
        MemoryBuffer resumeBuffer = new MemoryBuffer();
        resumeUpload = new Upload(resumeBuffer);
        resumeUpload.setAcceptedFileTypes("application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        resumeUpload.setMaxFiles(1);

        // MultiSelect ComboBox for roles
        rolesComboBox = new MultiSelectComboBox<>("Roles");
        rolesComboBox.setItems(Role.values());

        // Create dynamic certifications input area
        certificationsLayout = new VerticalLayout();
        addCertificationButton = new Button("Add Certification", event -> addCertificationInput());
        certificationsLayout.add(createCertificationField()); // Initially one certification field
        certificationsLayout.add(addCertificationButton);

        // Dynamic field to ask about kids if marital status is "Married"
        hasKidsComboBox = new ComboBox<>("Do you have kids?");
        hasKidsComboBox.setItems("Yes", "No");
        hasKidsComboBox.setVisible(false);
        hasKidsComboBox.addValueChangeListener(event -> onKidsSelectionChanged(event.getValue()));

        numberOfChildrenField = new TextField("Number of children");
        numberOfChildrenField.setVisible(false);

        // Submit button
        submitButton = new Button("Submit", event -> submitStaffData());

        // Layout the form
        FormLayout formLayout = new FormLayout(
            firstNameField,
            middleNameField,
            lastNameField,
            positionField,
            kraPinField,
            residenceField,
            maritalStatusComboBox,
            sexComboBox,
            dateOfBirthField,
            photoUpload,
            resumeUpload,
            certificationsLayout,
            rolesComboBox,
            hasKidsComboBox,
            numberOfChildrenField,
            submitButton
        );

        add(formLayout);

        // Listen for marital status changes to show the kids-related fields
        maritalStatusComboBox.addValueChangeListener(event -> onMaritalStatusChanged(event.getValue()));
    }

    private void onMaritalStatusChanged(String maritalStatus) {
        if ("Married".equals(maritalStatus)) {
            hasKidsComboBox.setVisible(true);
        } else {
            hasKidsComboBox.setVisible(false);
            numberOfChildrenField.setVisible(false);
        }
    }

    private void onKidsSelectionChanged(String value) {
        if ("Yes".equals(value)) {
            numberOfChildrenField.setVisible(true);
        } else {
            numberOfChildrenField.setVisible(false);
        }
    }

    private Component createCertificationField() {
        TextField certificationField = new TextField("Certification");
        return certificationField;
    }

    private void addCertificationInput() {
        certificationsLayout.add(createCertificationField());
    }

    private void submitStaffData() {
        // Validate the form fields
        if (firstNameField.isEmpty() || lastNameField.isEmpty()) {
            UI.getCurrent().getPage().executeJs("alert('Please fill in the required fields!');");
            return;
        }

        // Create the Staff object
        Staff staff = new Staff();
        staff.setFirstName(firstNameField.getValue());
        staff.setMiddleName(middleNameField.getValue());
        staff.setLastName(lastNameField.getValue());
        staff.setPosition(positionField.getValue());
        staff.setKraPin(kraPinField.getValue());
        staff.setResidence(residenceField.getValue());
        staff.setMaritalStatus(Staff.MaritalStatus.valueOf(maritalStatusComboBox.getValue().toUpperCase()));
        staff.setSex(Staff.Sex.valueOf(sexComboBox.getValue().toUpperCase()));
        staff.setDateOfBirth(dateOfBirthField.getValue());

        // Collect certifications
        staff.setCertifications(collectCertifications());

        // Assign roles
        staff.setRoles(rolesComboBox.getValue());

        // Handle kids
        staff.setNumberOfChildren(hasKidsComboBox.getValue().equals("Yes") ? Integer.parseInt(numberOfChildrenField.getValue()) : 0);

        // Save the staff member
        staffService.save(staff);

        // Navigate to the staff list or confirmation page
        UI.getCurrent().navigate("staff-list");
    }

    private Set<String> collectCertifications() {
        Set<String> certifications = new HashSet<>();
        certificationsLayout.getChildren().forEach(component -> {
            if (component instanceof TextField) {
                String certification = ((TextField) component).getValue();
                if (!certification.isEmpty()) {
                    certifications.add(certification);
                }
            }
        });
        return certifications;
    }
}
