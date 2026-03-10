package com.example.student.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InvoiceResponse {

    private Long id;

    private Long studentId;

    private String term;

    private Double amountTotal;

    private Double amountPaid;

    private String status;

    private LocalDate dueDate;

    private LocalDateTime createdAt;
}