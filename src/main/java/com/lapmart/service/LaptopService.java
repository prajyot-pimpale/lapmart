package com.lapmart.service;

import java.math.BigDecimal;
import java.util.List;

import com.lapmart.dto.CategoryRequest;
import com.lapmart.dto.LaptopRequest;
import com.lapmart.dto.LaptopResponse;

// Define the contract — what operations are available
// Implementation is in LaptopServiceImpl
public interface LaptopService {

    // Admin operations
    LaptopResponse addLaptop(LaptopRequest request);
    LaptopResponse updateLaptop(Long id, LaptopRequest request);
    void deleteLaptop(Long id);
    String addCategory(CategoryRequest request);

    // Public operations
    List<LaptopResponse> getAllLaptops();
    LaptopResponse getLaptopById(Long id);
    List<LaptopResponse> searchLaptops(String keyword, String brand, BigDecimal maxPrice, Long categoryId);
}