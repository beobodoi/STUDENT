package com.example.student.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeRequest {

    @NotNull
    private Long enrollmentId;

    @DecimalMin("0.0")
    @DecimalMax("4.0")
    private Double score;
}