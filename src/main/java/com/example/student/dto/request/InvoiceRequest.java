package com.example.student.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private String term;

    @NotNull
    private Double amountTotal;

    private LocalDate dueDate;
}