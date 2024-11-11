package com.admin.school.views.classview;

import com.admin.school.entity.Class;
import com.admin.school.entity.Teacher;
import com.admin.school.services.ClassService;
import com.admin.school.services.TeacherService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Route("class-view") // This is the URL to access this view
public class ClassView extends VerticalLayout {

    private final ClassService classService;
    private final TeacherService teacherService;

    private Grid<Class> classGrid;
    private ComboBox<Teacher> teacherComboBox; // ComboBox for selecting a teacher
    private TextField searchField;
    private Button addClassButton;

    public ClassView(ClassService classService, TeacherService teacherService) {
        this.classService = classService;
        this.teacherService = teacherService;

        // Initialize the components
        classGrid = new Grid<>(Class.class);
        teacherComboBox = new ComboBox<>("Assign Teacher");
        searchField = new TextField("Search Classes");
        addClassButton = new Button("Add New Class");

        // Layout setup
        add(searchField, addClassButton, teacherComboBox, classGrid);

        // Configure the grid to show class details
        classGrid.setColumns("name", "grade"); // Show class name and grade
        classGrid.addColumn(clazz -> clazz.getStudents() != null ? clazz.getStudents().size() : 0)
                .setHeader("No. of Students");

        // Event listener for search field to filter classes by name
        searchField.addValueChangeListener(event -> filterClasses(event.getValue()));

        // Event listener for adding new class
        addClassButton.addClickListener(event -> showAddClassForm());

        // Load available teachers into ComboBox
        loadTeachers();

        // Assign a teacher to the selected class
        teacherComboBox.addValueChangeListener(event -> assignTeacherToClass(event.getValue()));

        // Load classes into the grid
        updateClassGrid();
    }

    // Load the available teachers into the ComboBox
    private void loadTeachers() {
        // Fetch all teachers with pagination
        List<Teacher> teachers = teacherService.listTeachers(PageRequest.of(0, 10)).getContent();
        teacherComboBox.setItems(teachers);

        // Set item label generator to display teacher's full name
        teacherComboBox.setItemLabelGenerator(Teacher::getFullName);
    }

    // Filter classes based on the name entered in the search field
    private void filterClasses(String name) {
        // Filter classes by name using the ClassService
        List<Class> filteredClasses = classService.filterByName(name);
        classGrid.setItems(filteredClasses);
    }

    // Method to update the grid with all classes from the service
    private void updateClassGrid() {
        // Fetch all classes from the ClassService and display them in the grid
        List<Class> classes = classService.listAll();
        classGrid.setItems(classes);
    }

    // Show the form to add a new class
    private void showAddClassForm() {
        // For now, we will log this and later replace it with the actual navigation or dialog
        System.out.println("Navigate to form to add a class");
    }

    // Assign a selected teacher to the selected class in the grid
    private void assignTeacherToClass(Teacher teacher) {
        if (teacher != null) {
            // Get the currently selected class from the grid
            Class selectedClass = classGrid.asSingleSelect().getValue();
            if (selectedClass != null) {
                selectedClass.setClassTeacher(teacher); // Assuming there's a setClassTeacher method
                classService.save(selectedClass); // Persist the updated class with the assigned teacher
            }
        }
    }
}
