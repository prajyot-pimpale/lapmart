package com.lapmart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lapmart.entity.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Check if a specific laptop already exists in a specific cart
    // Used to avoid duplicate items — instead update quantity
    Optional<CartItem> findByCartIdAndLaptopId(Long cartId, Long laptopId);

    // Delete all items of a cart (used after order is placed)
    void deleteByCartId(Long cartId);
}