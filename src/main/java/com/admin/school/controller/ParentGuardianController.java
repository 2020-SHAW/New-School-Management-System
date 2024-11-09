package com.admin.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.admin.school.entity.ParentGuardian;
import com.admin.school.services.ParentGuardianService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/guardians")
public class ParentGuardianController {

    private final ParentGuardianService guardianService;

    @Autowired
    public ParentGuardianController(ParentGuardianService guardianService) {
        this.guardianService = guardianService;
    }

    @PostMapping
    public ParentGuardian createGuardian(@RequestBody ParentGuardian guardian) {
        return guardianService.saveGuardian(guardian);
    }

    @GetMapping("/{id}")
    public Optional<ParentGuardian> getGuardianById(@PathVariable("id") String guardianId) {
        return guardianService.getGuardianById(guardianId);
    }

    @GetMapping
    public List<ParentGuardian> getAllGuardians() {
        return guardianService.getAllGuardians();
    }

    @DeleteMapping("/{id}")
    public void deleteGuardian(@PathVariable("id") String guardianId) {
        guardianService.deleteGuardian(guardianId);
    }
}
