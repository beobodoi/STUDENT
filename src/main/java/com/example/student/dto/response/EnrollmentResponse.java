package com.example.student.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EnrollmentResponse {

    private Long id;

    private Long studentId;

    private Long classId;

    private LocalDateTime enrolledAt;
}