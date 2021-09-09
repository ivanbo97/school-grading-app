package com.ivanboyukliev.schoolgradingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivanboyukliev.schoolgradingapp.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
