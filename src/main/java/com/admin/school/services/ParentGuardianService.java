package com.admin.school.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.school.entity.ParentGuardian;
import com.admin.school.repository.ParentGuardianRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ParentGuardianService {

    private final ParentGuardianRepository guardianRepository;

    @Autowired
    public ParentGuardianService(ParentGuardianRepository guardianRepository) {
        this.guardianRepository = guardianRepository;
    }

    // Create or Update a guardian
    public ParentGuardian saveGuardian(ParentGuardian guardian) {
        return guardianRepository.save(guardian);
    }

    // Find a guardian by ID
    public Optional<ParentGuardian> getGuardianById(String guardianId) {
        return guardianRepository.findById(guardianId);
    }

    // Find all guardians
    public List<ParentGuardian> getAllGuardians() {
        return guardianRepository.findAll();
    }

    // Delete a guardian by ID
    public void deleteGuardian(String guardianId) {
        guardianRepository.deleteById(guardianId);
    }
}
