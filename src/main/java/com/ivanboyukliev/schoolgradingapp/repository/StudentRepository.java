package com.ivanboyukliev.schoolgradingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ivanboyukliev.schoolgradingapp.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
