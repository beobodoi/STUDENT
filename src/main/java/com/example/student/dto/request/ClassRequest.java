package com.example.student.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassRequest {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotNull
    private Integer capacity;

    private LocalDate startDate;

    private LocalDate endDate;
}