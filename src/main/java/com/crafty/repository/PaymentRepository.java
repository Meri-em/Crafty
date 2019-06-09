package com.crafty.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crafty.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String>  {

}
