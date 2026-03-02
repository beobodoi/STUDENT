package com.example.student.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Student> findByGpaBetween(Double min, Double max);

    List<Student> findByActiveTrue();
}