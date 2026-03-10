package com.example.student.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.student.entity.*;
import com.example.student.repository.*;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl {

    private final InvoiceRepository invoiceRepo;
    private final PaymentRepository paymentRepo;

    @Transactional
    public void payInvoice(Long invoiceId, Double amount){

        Invoice invoice = invoiceRepo.findById(invoiceId).orElseThrow();

        if(invoice.getAmountPaid() + amount > invoice.getAmountTotal()){
            throw new RuntimeException("Overpayment");
        }

        Payment payment = new Payment();
        payment.setInvoice(invoice);
        payment.setAmount(amount);
        payment.setPaidAt(LocalDateTime.now());

        paymentRepo.save(payment);

        invoice.setAmountPaid(invoice.getAmountPaid() + amount);

        if(invoice.getAmountPaid().equals(invoice.getAmountTotal()))
            invoice.setStatus("PAID");
        else
            invoice.setStatus("PARTIALLY_PAID");

        invoiceRepo.save(invoice);
    }
}