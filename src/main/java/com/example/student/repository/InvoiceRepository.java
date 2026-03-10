package com.example.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice,Long>{

    List<Invoice> findByStudentId(Long studentId);
}