package com.admin.school.views.StaffAttendance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.admin.school.Application;
import com.admin.school.data.Role;
import com.admin.school.entity.Attendance;
import com.admin.school.entity.Staff;
import com.admin.school.services.AttendanceService;
import com.vaadin.flow.component.combobox.ComboBox;



@SpringBootApplication
public class StaffAttendanceApp extends Application {

    @Autowired
    private AttendanceService attendanceService;

    private ComboBox<Role> roleComboBox;
    private TableView<Staff> staffTableView;
    private Button submitButton;
    private ObservableList<Staff> staffList;

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(StaffAttendanceApp.class);
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // Inject AttendanceService
        attendanceService = new AttendanceService(); // You will likely use Spring for dependency injection in a Spring Boot app

        roleComboBox = new ComboBox<>();
        roleComboBox.setItems(FXCollections.observableArrayList(Role.values()));
        roleComboBox.getSelectionModel().selectFirst();

        staffList = FXCollections.observableArrayList();
        staffTableView = new TableView<>();

        TableColumn<Staff, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(() ->
                cellData.getValue().getFirstName() + " " + cellData.getValue().getLastName()));
        
        TableColumn<Staff, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(() ->
                cellData.getValue().getId()));
        
        TableColumn<Staff, Boolean> presentColumn = new TableColumn<>("Present");
        presentColumn.setCellValueFactory(cellData -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(false);
            checkBox.setStyle("-fx-background-color: red;");
            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    checkBox.setStyle("-fx-background-color: green;");
                } else {
                    checkBox.setStyle("-fx-background-color: red;");
                }
            });
            return new SimpleObjectProperty<>(checkBox);
        });
        
        staffTableView.getColumns().addAll(nameColumn, idColumn, presentColumn);
        staffTableView.setItems(staffList);

        submitButton = new Button("Submit Attendance");
        submitButton.setOnAction(event -> submitAttendance());

        roleComboBox.setOnAction(event -> filterByRole(roleComboBox.getValue()));

        VBox root = new VBox(10, roleComboBox, staffTableView, submitButton);
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Staff Attendance Register");
        stage.show();
    }

    private void filterByRole(Role role) {
        List<Staff> filteredStaff = attendanceService.getStaffByRole(role);
        staffList.setAll(filteredStaff);
    }

    private void submitAttendance() {
        LocalDate today = LocalDate.now();
        List<Attendance> attendances = new ArrayList<>();

        for (Staff staff : staffList) {
            boolean isPresent = /* Check if checkbox is selected */;
            Attendance attendance = new Attendance(staff, isPresent, today);
            attendances.add(attendance);
        }

        // Save attendance to the database
        attendanceService.saveAttendance(attendances);

        submitButton.setDisable(true);  // Disable submit button after submission
    }
}
