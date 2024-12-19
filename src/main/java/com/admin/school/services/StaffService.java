package com.admin.school.services;

import com.admin.school.entity.Staff;
import com.admin.school.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    // Save a new staff member or update an existing one
    public Staff save(Staff staff) {
        return staffRepository.save(staff); // Save or update the staff member
    }

    // Get a staff member by their ID
    public Optional<Staff> getById(String id) {
        return staffRepository.findById(id); // Fetch staff by ID
    }

    // Get all staff members
    public List<Staff> getAll() {
        return staffRepository.findAll(); // Get all staff members
    }

    // Delete a staff member by their ID
    public void deleteById(String id) {
        staffRepository.deleteById(id); // Delete a staff member by ID
    }
}
