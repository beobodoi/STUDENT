package com.example.student.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.student.entity.ClassEntity;
import com.example.student.entity.Enrollment;
import com.example.student.entity.Student;
import com.example.student.repository.ClassRepository;
import com.example.student.repository.EnrollmentRepository;
import com.example.student.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl {

    private final EnrollmentRepository enrollmentRepo;
    private final StudentRepository studentRepo;
    private final ClassRepository classRepo;

    @Transactional
    public Enrollment enroll(Long studentId, Long classId){

        Student student = studentRepo.findById(studentId).orElseThrow();

        ClassEntity clazz = classRepo.findById(classId).orElseThrow();

        int current = enrollmentRepo.countByClazzId(classId);

        if(current >= clazz.getCapacity()){
            throw new RuntimeException("Class is full");
        }

        Enrollment e = new Enrollment();
        e.setStudent(student);
        e.setClazz(clazz);

        return enrollmentRepo.save(e);
    }
}