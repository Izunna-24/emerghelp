package com.emerghelp.emerghelp.data.repositories;


import com.emerghelp.emerghelp.data.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaystackPaymentRepository extends JpaRepository<Payment, String> {
}