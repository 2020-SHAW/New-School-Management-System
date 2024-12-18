package com.admin.school.views.FinanceView;

import com.admin.school.entity.Finance;
import com.admin.school.entity.Student;
import com.admin.school.services.FinanceService;
import com.admin.school.services.StudentService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;

@Route("finance")
@Menu(order = 16, icon = "line-awesome/svg/th-solid.svg")
@RolesAllowed("USER")
public class FinanceView extends VerticalLayout {

    private final FinanceService financeService;
    private final StudentService studentService;

    // UI elements
    private ComboBox<Student> studentComboBox;
    private TextField studentIdField;  // Student ID input as a string
    private TextField studentSearchField;  // Search field for student by ID
    private TextField amountDueField;
    private TextField amountPaidField;
    private ComboBox<String> paymentMethodComboBox;
    private TextField chequeNumberField;
    private TextField transactionIdField;
    private TextField mpesaCodeField;
    private Button submitButton;
    private Div balanceDiv; // To show the balance or overpayment message

    @Autowired
    public FinanceView(FinanceService financeService, StudentService studentService) {
        this.financeService = financeService;
        this.studentService = studentService;

        // Initialize components
        studentComboBox = new ComboBox<>("Select Student");
        studentComboBox.setItems(studentService.getAllStudents());

        studentIdField = new TextField("Student ID");
        studentIdField.setEnabled(false); // Read-only field for the student ID

        studentSearchField = new TextField("Search Student by ID");
        studentSearchField.addValueChangeListener(event -> searchStudentById(event.getValue()));

        amountDueField = new TextField("Amount Due");
        amountDueField.setEnabled(false);  // Prevent manual edits

        amountPaidField = new TextField("Amount Paid");

        paymentMethodComboBox = new ComboBox<>("Payment Method");
        paymentMethodComboBox.setItems("Cheque", "Bank Slip", "MPESA");

        chequeNumberField = new TextField("Cheque Number");
        transactionIdField = new TextField("Transaction ID");
        mpesaCodeField = new TextField("MPESA Code");

        // Initially hide payment-specific fields
        chequeNumberField.setVisible(false);
        transactionIdField.setVisible(false);
        mpesaCodeField.setVisible(false);

        // Show the appropriate field based on the selected payment method
        paymentMethodComboBox.addValueChangeListener(event -> {
            String paymentMethod = event.getValue();
            chequeNumberField.setVisible("Cheque".equals(paymentMethod));
            transactionIdField.setVisible("Bank Slip".equals(paymentMethod));
            mpesaCodeField.setVisible("MPESA".equals(paymentMethod));
        });

        // Balance or overpayment display
        balanceDiv = new Div();
        balanceDiv.addClassName("balance-div");
        
        // Submit button to save finance information
        submitButton = new Button("Submit Payment", event -> savePayment());

        // Layout the form
        FormLayout formLayout = new FormLayout(
                studentSearchField,
                studentComboBox,
                studentIdField,
                amountDueField,
                amountPaidField,
                paymentMethodComboBox,
                chequeNumberField,
                transactionIdField,
                mpesaCodeField,
                submitButton,
                balanceDiv // Add balanceDiv to the form
        );

        add(formLayout);

        studentComboBox.addValueChangeListener(event -> loadStudentFinance(event.getValue()));
    }

    private void searchStudentById(String studentId) {
        if (studentId != null && !studentId.trim().isEmpty()) {
            studentService.get(studentId).ifPresentOrElse(student -> {
                studentComboBox.setValue(student); // Set the selected student
                loadStudentFinance(student); // Load the finance information
            }, () -> {
                UI.getCurrent().getPage().executeJs("alert('Student not found!');");
            });
        } else {
            studentComboBox.setItems(studentService.getAllStudents()); // Reset if empty
        }
    }

    private void loadStudentFinance(Student selectedStudent) {
        if (selectedStudent != null) {
        	studentIdField.setValue(selectedStudent.getId());// Display the student ID
            Finance finance = financeService.getFinanceByStudent(selectedStudent);
            if (finance != null) {
                amountDueField.setValue(String.valueOf(finance.getAmountDue()));
                updateBalanceDisplay(finance);
            }
        }
    }

    private void updateBalanceDisplay(Finance finance) {
        if (finance != null) {
            double amountDue = finance.getAmountDue();
            double amountPaid = Double.parseDouble(amountPaidField.getValue());

            // Calculate balance or overpayment
            double balance = amountDue - amountPaid;

            // Update balance message
            if (balance > 0) {
                balanceDiv.setText("Remaining Balance: " + balance);
                balanceDiv.getStyle().set("color", "red"); // Remaining balance in red
            } else if (balance < 0) {
                balanceDiv.setText("Overpaid Amount: " + Math.abs(balance));
                balanceDiv.getStyle().set("color", "green"); // Overpaid amount in green
            } else {
                balanceDiv.setText("Payment Completed");
                balanceDiv.getStyle().set("color", "black"); // Payment completed, black text
            }
        }
    }

    private void savePayment() {
        Student selectedStudent = studentComboBox.getValue();
        if (selectedStudent != null) {
            double amountPaid = Double.parseDouble(amountPaidField.getValue());
            String paymentMethod = paymentMethodComboBox.getValue();
            String chequeNumber = chequeNumberField.getValue();
            String transactionId = transactionIdField.getValue();
            String mpesaCode = mpesaCodeField.getValue();

            Finance finance = financeService.getFinanceByStudent(selectedStudent);
            
            if (finance != null) {
                // Update the finance record with the new payment information
                finance.setAmountDue(finance.getAmountDue() - amountPaid); // Adjust the due amount
                financeService.saveFinance(finance); // Save the updated finance data

                // Notify the user
                UI.getCurrent().getPage().executeJs("alert('Payment has been successfully saved!');");

                // Optionally clear the form
                amountPaidField.clear();
                paymentMethodComboBox.clear();
                chequeNumberField.clear();
                transactionIdField.clear();
                mpesaCodeField.clear();
            }
        }
    }
}
