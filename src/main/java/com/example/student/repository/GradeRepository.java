package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Grade;

public interface GradeRepository extends JpaRepository<Grade,Long> {
}