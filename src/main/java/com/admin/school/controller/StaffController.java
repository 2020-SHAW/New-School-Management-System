package com.admin.school.controller;

import com.admin.school.entity.Staff;
import com.admin.school.services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    // Create or update a staff member
    @PostMapping
    public ResponseEntity<Staff> createStaff(@RequestBody Staff staff) {
        Staff savedStaff = staffService.save(staff);
        return ResponseEntity.ok(savedStaff);
    }

    // Get all staff members
    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        List<Staff> staffList = staffService.getAll();
        return ResponseEntity.ok(staffList);
    }

    // Get a single staff member by ID
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(@PathVariable String id) {
        Optional<Staff> staff = staffService.getById(id);
        if (staff.isPresent()) {
            return ResponseEntity.ok(staff.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a staff member by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable String id) {
        staffService.deleteById(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

    // Update a staff member by ID
    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable String id, @RequestBody Staff staff) {
        Optional<Staff> existingStaff = staffService.getById(id);
        if (existingStaff.isPresent()) {
            staff.setId(id); // Ensure the ID is not changed
            Staff updatedStaff = staffService.save(staff);
            return ResponseEntity.ok(updatedStaff);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
