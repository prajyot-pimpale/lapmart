package com.lapmart.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lapmart.entity.Laptop;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long>{
	
	// Find all laptops of a specific brand
    // SQL: SELECT * FROM laptops WHERE brand = ?
    List<Laptop> findByBrand(String brand);

    // Find laptops under a certain price
    // SQL: SELECT * FROM laptops WHERE price <= ?
    List<Laptop> findByPriceLessThanEqual(BigDecimal price);

    // Find laptops by category id
    // "category_id" is accessed via the relation: category.id
    List<Laptop> findByCategoryId(Long categoryId);

    // Find laptops by brand AND under a max price
    List<Laptop> findByBrandAndPriceLessThanEqual(String brand, BigDecimal maxPrice);

    // Custom JPQL query — search by keyword in name or brand (case-insensitive)
    // JPQL uses class/field names, not table/column names
    // :keyword is a named parameter
    @Query("SELECT l FROM Laptop l WHERE " +
           "LOWER(l.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(l.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Laptop> searchByKeyword(@Param("keyword") String keyword);

    // Find laptops with stock available
    List<Laptop> findByStockGreaterThan(Integer stock);
}
