package com.example.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long>{

    List<Payment> findByInvoiceId(Long invoiceId);
}