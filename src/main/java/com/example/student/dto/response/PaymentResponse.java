package com.example.student.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentResponse {

    private Long id;

    private Long invoiceId;

    private Double amount;

    private String method;

    private LocalDateTime paidAt;
}