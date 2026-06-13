package com.lapmart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lapmart.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Used when admin tries to add a duplicate category
    boolean existsByName(String name);

    // Used to fetch category by name
    Optional<Category> findByName(String name);
}