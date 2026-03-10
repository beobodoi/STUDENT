package com.example.student.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GradeResponse {

    private Long id;

    private Long enrollmentId;

    private Double score;

    private LocalDateTime gradedAt;
}