// src/main/java/com/management/school/controller/FinanceController.java
package com.admin.school.controller;

import com.admin.school.entity.Finance;
import com.admin.school.services.FinanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/finances")
public class FinanceController {

    private final FinanceService financeService;

    // Constructor injection for the FinanceService
    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    // POST - Create a new finance record
    @PostMapping
    public ResponseEntity<Finance> createFinance(@RequestBody Finance finance) {
        Finance savedFinance = financeService.save(finance);
        return new ResponseEntity<>(savedFinance, HttpStatus.CREATED);
    }

    // GET - Retrieve a finance record by ID
    @GetMapping("/{id}")
    public ResponseEntity<Finance> getFinanceById(@PathVariable Long id) {
        Optional<Finance> finance = financeService.get(id);
        return finance.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // GET - Retrieve all finance records
    @GetMapping
    public List<Finance> getAllFinances() {
        return financeService.listAll();  // Assuming you implement listAll() in the service
    }

    // PUT - Update an existing finance record
    @PutMapping("/{id}")
    public ResponseEntity<Finance> updateFinance(@PathVariable Long id, @RequestBody Finance finance) {
        Optional<Finance> existingFinance = financeService.get(id);
        if (existingFinance.isPresent()) {
            finance.setId(id);  // Ensure the correct ID is set for update
            Finance updatedFinance = financeService.save(finance);
            return ResponseEntity.ok(updatedFinance);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE - Delete a finance record by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFinance(@PathVariable Long id) {
        Optional<Finance> finance = financeService.get(id);
        if (finance.isPresent()) {
            financeService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
