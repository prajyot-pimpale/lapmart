package com.lapmart.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lapmart.dto.LaptopResponse;
import com.lapmart.service.LaptopService;

@RestController
@RequestMapping("/api/laptops")
public class LaptopController {

    private final LaptopService laptopService;
    
    

    public LaptopController(LaptopService laptopService) {
		this.laptopService = laptopService;
	}

	// GET /api/laptops
    @GetMapping
    public ResponseEntity<List<LaptopResponse>> getAllLaptops() {
        return ResponseEntity.ok(laptopService.getAllLaptops());
    }

    // GET /api/laptops/5
    @GetMapping("/{id}")
    public ResponseEntity<LaptopResponse> getLaptopById(@PathVariable Long id) {
        return ResponseEntity.ok(laptopService.getLaptopById(id));
    }

    // GET /api/laptops/search?keyword=dell
    // GET /api/laptops/search?brand=HP&maxPrice=60000
    // GET /api/laptops/search?categoryId=2
    // All params are optional — @RequestParam(required = false)
    @GetMapping("/search")
    public ResponseEntity<List<LaptopResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Long categoryId) {

        return ResponseEntity.ok(
            laptopService.searchLaptops(keyword, brand, maxPrice, categoryId)
        );
    }
}