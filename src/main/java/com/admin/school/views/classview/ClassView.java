package com.admin.school.views.classview;

import com.admin.school.entity.Class;
import com.admin.school.services.ClassService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Optional;

@PageTitle("Class View")
@Route("class-view/:classID?/:action?(edit)")
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class ClassView extends Div implements BeforeEnterObserver {

    private static final String CLASS_ID = "classID";
    private static final String CLASS_EDIT_ROUTE_TEMPLATE = "class-view/%s/edit";

    private final Grid<Class> grid = new Grid<>(Class.class, false);
    private TextField nameField;
    private TextField gradeField;
    private Checkbox isActiveField;
    private TextField searchField;
    private Button searchButton;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Class> binder;
    private Class selectedClass;
    private final ClassService classService;

    public ClassView(ClassService classService) {
        this.classService = classService;
        addClassNames("master-detail-view");

        // Create UI components
        SplitLayout splitLayout = new SplitLayout();
        createSearchField();
        createSearchButton();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(searchField, searchButton, splitLayout);

        // Configure Grid
        grid.addColumn("name").setAutoWidth(true);
        grid.addColumn("grade").setAutoWidth(true);
        grid.addColumn(clazz -> clazz.getStudents() != null ? clazz.getStudents().size() : 0)
                .setHeader("No. of Students").setAutoWidth(true);

        // Add the column for active status with green/red dot using ComponentRenderer
        grid.addColumn(new ComponentRenderer<>(clazz -> {
            String dotColor = clazz.getIsActive() ? "green" : "red";
            Div statusDot = new Div();
            statusDot.getStyle().set("width", "10px")
                    .set("height", "10px")
                    .set("border-radius", "50%")
                    .set("background-color", dotColor);
            return statusDot;
        })).setHeader("Status").setAutoWidth(true).setSortable(false);

        // Initially load grid
        grid.setItems(query -> classService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // When a row is selected or deselected, populate the form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(CLASS_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(ClassView.class);
            }
        });

        // Binder setup for form validation
        binder = new BeanValidationBinder<>(Class.class);

        // Explicitly bind fields here
        binder.forField(nameField)
              .bind(Class::getName, Class::setName);

        binder.forField(gradeField)
              .asRequired("Grade is required") // Make the Grade field required
              .bind(Class::getGrade, Class::setGrade);

        binder.forField(isActiveField)
              .bind(Class::getIsActive, Class::setIsActive);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.selectedClass == null) {
                    this.selectedClass = new Class(); // Prevent NullPointerException
                }
                binder.writeBean(this.selectedClass);
                classService.save(this.selectedClass);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(ClassView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    private void createSearchField() {
        searchField = new TextField();
        searchField.setPlaceholder("Search classes...");
        searchField.setClearButtonVisible(true);
        searchField.setWidthFull();
    }

    private void createSearchButton() {
        searchButton = new Button("Search");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchButton.addClickListener(e -> {
            filterClasses(searchField.getValue());  // Trigger search on button click
        });
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        nameField = new TextField("Class Name");
        gradeField = new TextField("Grade");
        isActiveField = new Checkbox("Is Active");
        formLayout.add(nameField, gradeField, isActiveField);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void filterClasses(String searchTerm) {
        grid.setItems(query -> classService.findBySearchTerm(searchTerm,
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.getDataProvider().refreshAll();  // Ensure grid refreshes
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Class value) {
        this.selectedClass = value;
        binder.readBean(this.selectedClass);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> classId = event.getRouteParameters().get(CLASS_ID).map(Long::parseLong);
        if (classId.isPresent()) {
            Optional<Class> classFromBackend = classService.get(classId.get());
            if (classFromBackend.isPresent()) {
                populateForm(classFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested class was not found, ID = %s", classId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(ClassView.class);
            }
        }
    }
}
