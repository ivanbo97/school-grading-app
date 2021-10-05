package com.ivanboyukliev.schoolgradingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivanboyukliev.schoolgradingapp.domain.Course;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findCourseByName(String courseName);
}
