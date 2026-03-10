package com.example.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.ClassEntity;

public interface ClassRepository extends JpaRepository<ClassEntity,Long> {
}