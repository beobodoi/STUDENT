package com.example.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.student.entity.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long>{

    int countByClazzId(Long classId);

    List<Enrollment> findByClazzId(Long classId);

    List<Enrollment> findByStudentId(Long studentId);
}