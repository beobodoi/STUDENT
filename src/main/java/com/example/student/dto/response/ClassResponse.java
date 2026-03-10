package com.example.student.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClassResponse {

    private Long id;

    private String code;

    private String name;

    private Integer capacity;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime createdAt;
}