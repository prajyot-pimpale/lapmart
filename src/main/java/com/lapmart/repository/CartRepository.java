package com.lapmart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lapmart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Fetch cart by user's id
    // Navigates relation: cart.user.id
    Optional<Cart> findByUserId(Long userId);
}