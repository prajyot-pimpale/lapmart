package com.lapmart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lapmart.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find payment by Razorpay's order ID (used during payment verification)
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);

    // Find payment linked to our order
    Optional<Payment> findByOrderId(Long orderId);
}