package com.lapmart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lapmart.dto.CategoryRequest;
import com.lapmart.dto.LaptopRequest;
import com.lapmart.dto.LaptopResponse;
import com.lapmart.service.LaptopService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')") // Every method in this controller = ADMIN only
public class AdminController {

    private final LaptopService laptopService;

    public AdminController(LaptopService laptopService) {
		this.laptopService = laptopService;
	}

	// POST /api/admin/categories
    @PostMapping("/categories")
    public ResponseEntity<String> addCategory(
            @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(laptopService.addCategory(request));
    }

    // POST /api/admin/laptops
    @PostMapping("/laptops")
    public ResponseEntity<LaptopResponse> addLaptop(
            @Valid @RequestBody LaptopRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(laptopService.addLaptop(request));
    }

    // PUT /api/admin/laptops/5
    @PutMapping("/laptops/{id}")
    public ResponseEntity<LaptopResponse> updateLaptop(
            @PathVariable Long id,
            @Valid @RequestBody LaptopRequest request) {
        return ResponseEntity.ok(laptopService.updateLaptop(id, request));
    }

    // DELETE /api/admin/laptops/5
    @DeleteMapping("/laptops/{id}")
    public ResponseEntity<String> deleteLaptop(@PathVariable Long id) {
        laptopService.deleteLaptop(id);
        return ResponseEntity.ok("Laptop deleted successfully");
    }
}