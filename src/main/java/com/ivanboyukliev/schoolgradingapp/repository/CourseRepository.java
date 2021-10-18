package com.ivanboyukliev.schoolgradingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivanboyukliev.schoolgradingapp.domain.Course;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findCourseByName(String courseName);
}
