package com.ivanboyukliev.schoolgradingapp.repository;

import com.ivanboyukliev.schoolgradingapp.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByName(String studentName);
}
