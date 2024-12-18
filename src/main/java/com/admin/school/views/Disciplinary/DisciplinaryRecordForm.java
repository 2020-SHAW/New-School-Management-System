package com.admin.school.views.Disciplinary;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.admin.school.entity.DisciplinaryRecord;
import com.admin.school.entity.Student;
import com.admin.school.entity.Teacher;
import com.admin.school.services.DisciplinaryRecordService;
import com.admin.school.services.StudentService;
import com.admin.school.services.TeacherService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route("disciplinary-record-form")
@Menu(order = 15, icon = "line-awesome/svg/th-solid.svg")
@RolesAllowed("USER")
public class DisciplinaryRecordForm extends FormLayout {

    private DisciplinaryRecordService disciplinaryRecordService;
    private StudentService studentService;
    private TeacherService teacherService;

    private TextField idField = new TextField("Enter ID");
    private ComboBox<Student> studentComboBox = new ComboBox<>("Select Student");
    private ComboBox<Teacher> teacherComboBox = new ComboBox<>("Select Teacher");
    private TextArea rulesBroken = new TextArea("Rules Broken");
    private TextArea temporaryPunishment = new TextArea("Temporary Punishment");
    private ComboBox<DisciplinaryRecord.Decision> decisionComboBox = new ComboBox<>("Final Decision");
    private TextField suspensionWeeks = new TextField("Suspension Weeks");
    private DatePicker suspensionEndDate = new DatePicker("Suspension End Date");
    private Button saveButton = new Button("Save");

    // List of Kenyan holidays (Add more as needed)
    private static final List<LocalDate> kenyaHolidays = Arrays.asList(
        LocalDate.of(2024, 12, 25), // Christmas Day
        LocalDate.of(2024, 12, 12)  // Jamhuri Day
    );

    // Define school closure dates and session period
    private static final List<LocalDate> schoolClosureDates = Arrays.asList(
        LocalDate.of(2024, 8, 15),  // Example school break start
        LocalDate.of(2024, 8, 25)   // Example school break end
    );

    // Define the academic session end date (e.g., end of term)
    private static final LocalDate sessionEndDate = LocalDate.of(2024, 11, 30); // Example session end date

    @Autowired
    public DisciplinaryRecordForm(DisciplinaryRecordService disciplinaryRecordService,
                                  StudentService studentService,
                                  TeacherService teacherService) {
        this.disciplinaryRecordService = disciplinaryRecordService;
        this.studentService = studentService;
        this.teacherService = teacherService;

        // Populate ComboBoxes for students and teachers
        studentComboBox.setItems(studentService.getAllStudents());
        teacherComboBox.setItems(teacherService.listAllTeachers());

        // Setup decision combo box with possible disciplinary decisions
        decisionComboBox.setItems(DisciplinaryRecord.Decision.values());

        // Add components to the form layout
        add(idField, studentComboBox, teacherComboBox, rulesBroken, temporaryPunishment, decisionComboBox,
            suspensionWeeks, suspensionEndDate, saveButton);

        // Hide/Show student and teacher ComboBoxes based on ID input
        idField.addValueChangeListener(event -> {
            String id = idField.getValue();
            if (id != null && !id.isEmpty()) {
                detectAndPopulate(id);
            }
        });

        // Show suspension fields only when suspension is selected
        decisionComboBox.addValueChangeListener(event -> {
            if (event.getValue() == DisciplinaryRecord.Decision.SUSPENSION) {
                suspensionWeeks.setVisible(true);
                suspensionEndDate.setVisible(true);
            } else {
                suspensionWeeks.setVisible(false);
                suspensionEndDate.setVisible(false);
            }
        });

        // Save button logic for saving disciplinary record
        saveButton.addClickListener(e -> {
            DisciplinaryRecord disciplinaryRecord = new DisciplinaryRecord();
            disciplinaryRecord.setRulesBroken(rulesBroken.getValue());
            disciplinaryRecord.setTemporaryPunishment(temporaryPunishment.getValue());
            disciplinaryRecord.setFinalDecision(decisionComboBox.getValue());

            // Assign student or teacher disciplinary record
            if (studentComboBox.getValue() != null) {
                disciplinaryRecord.setStudent(studentComboBox.getValue());
                disciplinaryRecord.setTeacher(null);  // Set teacher to null
            } else if (teacherComboBox.getValue() != null) {
                disciplinaryRecord.setTeacher(teacherComboBox.getValue());
                disciplinaryRecord.setStudent(null);  // Set student to null
            }

            // Handle suspension logic if decision is suspension
            if (disciplinaryRecord.getFinalDecision() == DisciplinaryRecord.Decision.SUSPENSION) {
                // Handle suspension logic like suspensionWeeks and suspensionEndDate if needed
                disciplinaryRecord.setSuspensionWeeks(Integer.parseInt(suspensionWeeks.getValue()));
                disciplinaryRecord.setSuspensionEndDate(suspensionEndDate.getValue());
            }

            // Save the disciplinary record
            disciplinaryRecordService.createOrUpdateDisciplinaryRecord(disciplinaryRecord);
            Notification.show("Disciplinary Record saved successfully!");
        });

        // Set DatePicker's minimum date to today, disable weekends & holidays
        LocalDate today = LocalDate.now();
        suspensionEndDate.setMin(today.plusDays(1)); // Disable past dates

        // Disable weekends, holidays, school closures, and dates after session end
        suspensionEndDate.addValueChangeListener(event -> {
            LocalDate selectedDate = event.getValue();
            if (selectedDate != null && !isValidSuspensionEndDate(selectedDate)) {
                suspensionEndDate.setInvalid(true);
                Notification.show("The selected date is invalid (weekend, holiday, school closure, or after session end).");
            } else {
                suspensionEndDate.setInvalid(false);
            }
        });
    }

    // Method to check if the selected date is a valid suspension end date
    private boolean isValidSuspensionEndDate(LocalDate date) {
        // Check if the date is a weekend (Saturday or Sunday)
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }

        // Check if the date is a Kenyan holiday
        if (kenyaHolidays.contains(date)) {
            return false;
        }

        // Check if the date is within school closure dates
        if (schoolClosureDates.contains(date)) {
            return false;
        }

        // Check if the date is after the academic session's end date
        if (date.isAfter(sessionEndDate)) {
            return false;
        }

        return true;
    }

    // Method to auto-detect whether the entered ID corresponds to a student or teacher
    private void detectAndPopulate(String id) {
        // Check if ID corresponds to a student
        Optional<Student> studentOpt = studentService.get(id); // Ensure this method returns Optional<Student>
        studentOpt.ifPresentOrElse(student -> {
            studentComboBox.setValue(student);  // Set student if found
            teacherComboBox.clear(); // Clear teacher ComboBox
            studentComboBox.setVisible(true);
            teacherComboBox.setVisible(false);
        }, () -> {
            studentComboBox.clear();
            studentComboBox.setVisible(false);
        });

    	// Check if ID corresponds to a teacher
    	Optional<Teacher> teacherOpt = teacherService.getTeacherById(id); // Ensure this method returns Optional<Teacher>
    	teacherOpt.ifPresentOrElse(teacher -> {
    	    teacherComboBox.setValue(teacher); // Set teacher if found
    	    studentComboBox.clear(); // Clear student ComboBox
    	    teacherComboBox.setVisible(true);
    	    studentComboBox.setVisible(false);
    	}, () -> {
    	    teacherComboBox.clear();
    	    teacherComboBox.setVisible(false);
    	});
    }
}
