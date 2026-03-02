package com.example.student.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.student.dto.request.StudentRequest;
import com.example.student.dto.response.StudentResponse;
import com.example.student.entity.Student;
import com.example.student.exception.DuplicateEmailException;
import com.example.student.exception.InvalidOperationException;
import com.example.student.exception.ResourceNotFoundException;
import com.example.student.repository.StudentRepository;
import com.example.student.service.StudentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    public StudentResponse create(StudentRequest request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }

        Student student = Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .age(request.getAge())
                .gpa(request.getGpa())
                .active(true)
                .build();

        repository.save(student);

        return mapToResponse(student);
    }

    @Override
    public Page<StudentResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public StudentResponse getById(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return mapToResponse(student);
    }

    @Override
    public StudentResponse update(Long id, StudentRequest request) {

        Student student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (!student.getEmail().equals(request.getEmail())
                && repository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already exists");
        }

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());
        student.setGpa(request.getGpa());

        repository.save(student);

        return mapToResponse(student);
    }

    @Override
    public void delete(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        repository.delete(student);
    }

    @Override
    public void deactivate(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (!student.getActive()) {
            throw new InvalidOperationException("Student already inactive");
        }

        student.setActive(false);
        repository.save(student);
    }

    @Override
    public void activate(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (student.getActive()) {
            throw new InvalidOperationException("Student already active");
        }

        student.setActive(true);
        repository.save(student);
    }

    @Override
    public StudentResponse findByEmail(String email) {
        Student student = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return mapToResponse(student);
    }

    @Override
    public List<StudentResponse> filterByGpa(Double min, Double max) {
        return repository.findByGpaBetween(min, max)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<StudentResponse> getActiveStudents() {
        return repository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private StudentResponse mapToResponse(Student s) {
        return StudentResponse.builder()
                .id(s.getId())
                .name(s.getName())
                .email(s.getEmail())
                .age(s.getAge())
                .gpa(s.getGpa())
                .active(s.getActive())
                .createdAt(s.getCreatedAt())
                .build();
    }
}