package com.lapmart.service.impl;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lapmart.dto.CategoryRequest;
import com.lapmart.dto.LaptopRequest;
import com.lapmart.dto.LaptopResponse;
import com.lapmart.entity.Category;
import com.lapmart.entity.Laptop;
import com.lapmart.exception.ResourceNotFoundException;
import com.lapmart.repository.CategoryRepository;
import com.lapmart.repository.LaptopRepository;
import com.lapmart.service.LaptopService;

@Service
public class LaptopServiceImpl implements LaptopService {

    private final LaptopRepository laptopRepository;
    private final CategoryRepository categoryRepository;
    
    

    public LaptopServiceImpl(LaptopRepository laptopRepository, CategoryRepository categoryRepository) {
		this.laptopRepository = laptopRepository;
		this.categoryRepository = categoryRepository;
	}

	// ─── Add Category ─────────────────────────────────────────────
    @Override
    public String addCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Category already exists: " + request.getName());
        }

        Category category = new Category();
        category.setName(request.getName());
        categoryRepository.save(category);
        return "Category added: " + request.getName();
    }

    // ─── Add Laptop ───────────────────────────────────────────────
    @Override
    public LaptopResponse addLaptop(LaptopRequest request) {

        // Fetch category — throw 404 if not found
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Category not found with id: " + request.getCategoryId()
                ));

        Laptop laptop = new Laptop();
        laptop.setName(request.getName());
        laptop.setBrand(request.getBrand());
        laptop.setPrice(request.getPrice());
        laptop.setStock(request.getStock());
        laptop.setDescription(request.getDescription());
        laptop.setImageUrl(request.getImageUrl());
        laptop.setCategory(category);

        Laptop saved = laptopRepository.save(laptop);
        return mapToResponse(saved);
    }

    // ─── Update Laptop ────────────────────────────────────────────
    @Override
    public LaptopResponse updateLaptop(Long id, LaptopRequest request) {

        // Find existing laptop or throw 404
        Laptop laptop = laptopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Laptop not found with id: " + id
                ));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Category not found with id: " + request.getCategoryId()
                ));

        // Update fields
        laptop.setName(request.getName());
        laptop.setBrand(request.getBrand());
        laptop.setPrice(request.getPrice());
        laptop.setStock(request.getStock());
        laptop.setDescription(request.getDescription());
        laptop.setImageUrl(request.getImageUrl());
        laptop.setCategory(category);

        Laptop updated = laptopRepository.save(laptop); // save() = INSERT or UPDATE
        return mapToResponse(updated);
    }

    // ─── Delete Laptop ────────────────────────────────────────────
    @Override
    public void deleteLaptop(Long id) {
        if (!laptopRepository.existsById(id)) {
            throw new ResourceNotFoundException("Laptop not found with id: " + id);
        }
        laptopRepository.deleteById(id);
    }

    // ─── Get All Laptops ──────────────────────────────────────────
    @Override
    public List<LaptopResponse> getAllLaptops() {
        return laptopRepository.findAll()
                .stream()
                .map(this::mapToResponse) // convert each Laptop → LaptopResponse
                .collect(Collectors.toList());
    }

    // ─── Get Single Laptop ────────────────────────────────────────
    @Override
    public LaptopResponse getLaptopById(Long id) {
        Laptop laptop = laptopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Laptop not found with id: " + id
                ));
        return mapToResponse(laptop);
    }

    // ─── Search & Filter ──────────────────────────────────────────
    @Override
    public List<LaptopResponse> searchLaptops(String keyword,
                                               String brand,
                                               BigDecimal maxPrice,
                                               Long categoryId) {
        List<Laptop> results;

        // Priority: keyword search > brand filter > price filter > category filter
        if (keyword != null && !keyword.isEmpty()) {
            results = laptopRepository.searchByKeyword(keyword);

        } else if (brand != null && maxPrice != null) {
            results = laptopRepository.findByBrandAndPriceLessThanEqual(brand, maxPrice);

        } else if (brand != null) {
            results = laptopRepository.findByBrand(brand);

        } else if (maxPrice != null) {
            results = laptopRepository.findByPriceLessThanEqual(maxPrice);

        } else if (categoryId != null) {
            results = laptopRepository.findByCategoryId(categoryId);

        } else {
            results = laptopRepository.findAll(); // no filters → return all
        }

        return results.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ─── Helper: Entity → DTO ─────────────────────────────────────
    // Private method reused across all service methods
    private LaptopResponse mapToResponse(Laptop laptop) {
    	
    	LaptopResponse laptopResponse = new LaptopResponse();
    	laptopResponse.setId(laptop.getId());
    	laptopResponse.setName(laptop.getName());
        laptopResponse.setBrand(laptop.getBrand());
        laptopResponse.setPrice(laptop.getPrice());
        laptopResponse.setStock(laptop.getStock());
        laptopResponse.setDescription(laptop.getDescription());
        laptopResponse.setImageUrl(laptop.getImageUrl());
        laptopResponse.setCategoryName(laptop.getCategory().getName());
        
        return laptopResponse;
    }
}