package com.admin.school.views.Stats;

import com.admin.school.entity.Staff;
import com.admin.school.services.StaffService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route("staff-statistics")
@PageTitle("Statistics")
@Menu(order = 20, icon = "line-awesome/svg/th-solid.svg")
@RolesAllowed("ADMIN")
public class StaffStatisticsView extends VerticalLayout {

    private final StaffService staffService;
    private Grid<Staff> staffGrid;
    private ComboBox<String> roleComboBox;
    private DatePicker fromDatePicker;
    private DatePicker toDatePicker;
    private Button filterButton;
    
    // Stats display components
    private TextField totalStaffField;
    private TextField totalAbsentField;
    private TextField averageAgeField;

    @Autowired
    public StaffStatisticsView(StaffService staffService) {
        this.staffService = staffService;
        initUI();
        updateStats(staffService.getAll()); // Display stats initially
    }

    private void initUI() {
        // Filter components
        roleComboBox = new ComboBox<>("Role");
        roleComboBox.setItems("Cook", "Head Cook", "Caterer", "Security Guard", "Head Security", "Accountant", "Receptionist");
        roleComboBox.setPlaceholder("Select a role");

        fromDatePicker = new DatePicker("From Date");
        toDatePicker = new DatePicker("To Date");

        filterButton = new Button("Filter", event -> updateStaffData());

        // Stats display components
        totalStaffField = new TextField("Total Staff");
        totalStaffField.setReadOnly(true);
        
        totalAbsentField = new TextField("Absent Staff");
        totalAbsentField.setReadOnly(true);

        averageAgeField = new TextField("Average Age");
        averageAgeField.setReadOnly(true);

        // Grid to display staff members
        staffGrid = new Grid<>(Staff.class);
        staffGrid.addColumn(staff -> staff.getAge()).setHeader("Age (Years, Months, Days)");
        staffGrid.setColumns("firstName", "lastName", "position", "roles", "maritalStatus", "sex", "dateOfBirth");
        staffGrid.setSelectionMode(Grid.SelectionMode.NONE);

        // Layout for the filter form and stats
        VerticalLayout filterLayout = new VerticalLayout(roleComboBox, fromDatePicker, toDatePicker, filterButton);
        VerticalLayout statsLayout = new VerticalLayout(totalStaffField, totalAbsentField, averageAgeField);
        
        // Layout the whole UI
        add(filterLayout, statsLayout, staffGrid);
    }

    private void updateStaffData() {
        // Retrieve all staff data from the service
        List<Staff> allStaff = staffService.getAll();
        
        // Apply filters to the staff data
        String roleFilter = roleComboBox.getValue();
        Optional<java.time.LocalDate> fromDate = Optional.ofNullable(fromDatePicker.getValue());
        Optional<java.time.LocalDate> toDate = Optional.ofNullable(toDatePicker.getValue());

        List<Staff> filteredStaff = allStaff.stream()
            .filter(staff -> (roleFilter == null || staff.getRoles().contains(roleFilter)))
            .filter(staff -> (!fromDate.isPresent() || !staff.getDateOfBirth().isBefore(fromDate.get())))
            .filter(staff -> (!toDate.isPresent() || !staff.getDateOfBirth().isAfter(toDate.get())))
            .collect(Collectors.toList());

        // Set the filtered data in the grid
        staffGrid.setItems(filteredStaff);

        // Update statistics based on the filtered staff
        updateStats(filteredStaff);
    }

    private void updateStats(List<Staff> staffList) {
        // Calculate total staff
        totalStaffField.setValue(String.valueOf(staffList.size()));

        // Calculate total absent staff
        long absentCount = staffList.stream().filter(staff -> !staff.isPresent()).count();  // Assuming `isPresent` method
        totalAbsentField.setValue(String.valueOf(absentCount));

        // Calculate average age of staff by role
        double averageAge = staffList.stream()
            .mapToInt(staff -> {
                // Parse the age in years from the `getAge` method
                String[] ageParts = staff.getAge().split(" ");
                int years = Integer.parseInt(ageParts[0]);
                return years;  // Use the year part for average calculation
            })
            .average()
            .orElse(0.0);

        averageAgeField.setValue(String.format("%.2f", averageAge));
    }
}
