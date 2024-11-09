package com.admin.school.views.ParentGuardianView;


import com.admin.school.entity.ParentGuardian;
import com.admin.school.services.ParentGuardianService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
@PageTitle("Parent and Guardian")
@Menu(order = 13, icon = "line-awesome/svg/th-solid.svg")
@RolesAllowed("USER")
@Route("guardian")
public class ParentGuardianView extends VerticalLayout {

    private final ParentGuardianService guardianService;
    private final Grid<ParentGuardian> grid = new Grid<>(ParentGuardian.class);

    private final TextField firstName = new TextField("First Name");
    private final TextField middleName = new TextField("Middle Name");
    private final TextField lastName = new TextField("Last Name");
    private final NumberField phoneNumber = new NumberField("Phone Number");
    private final TextField email = new TextField("Email");
    private final TextField residence = new TextField("Residence");
    private final NumberField emergencyContact = new NumberField("Emergency Contact");

    private final Binder<ParentGuardian> binder = new Binder<>(ParentGuardian.class);
    private final Button saveButton = new Button("Save", event -> saveGuardian());

    public ParentGuardianView(ParentGuardianService guardianService) {
        this.guardianService = guardianService;

        configureForm();
        configureGrid();

        add(firstName, middleName, lastName, phoneNumber, email, residence, emergencyContact, saveButton, grid);
    }

    private void configureForm() {
        binder.bindInstanceFields(this);
        binder.setBean(new ParentGuardian());
    }

    private void configureGrid() {
        grid.setColumns("id", "firstName", "lastName", "phoneNumber", "emailAddress", "residence");
        grid.setItems(guardianService.getAllGuardians());
        grid.asSingleSelect().addValueChangeListener(event -> populateForm(event.getValue()));
    }

    private void populateForm(ParentGuardian guardian) {
        binder.setBean(guardian != null ? guardian : new ParentGuardian());
    }

    private void saveGuardian() {
        ParentGuardian guardian = binder.getBean();
        guardianService.saveGuardian(guardian);
        Notification.show("Guardian saved successfully!");
        refreshGrid();
    }

    private void refreshGrid() {
        grid.setItems(guardianService.getAllGuardians());
    }
}
