package com.example.student.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.student.dto.request.StudentRequest;
import com.example.student.dto.response.StudentResponse;

public interface StudentService {

    StudentResponse create(StudentRequest request);

    Page<StudentResponse> getAll(Pageable pageable);

    StudentResponse getById(Long id);

    StudentResponse update(Long id, StudentRequest request);

    void delete(Long id);

    void deactivate(Long id);

    void activate(Long id);

    StudentResponse findByEmail(String email);

    List<StudentResponse> filterByGpa(Double min, Double max);

    List<StudentResponse> getActiveStudents();
}