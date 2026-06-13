package com.lapmart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lapmart.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Fetch all orders placed by a specific user
    // Sorted by createdAt descending → newest order first
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Admin: find orders by status (e.g. all PENDING orders)
    List<Order> findByStatus(Order.OrderStatus status);
}