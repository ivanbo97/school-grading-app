package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseListDTO;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;

public interface CourseService {

    CourseListDTO findAllCourses();

    CourseDTO findCourseById(Long id);

    CourseDTO saveCourse(CourseDTO course) throws EntityValidationException;

    CourseDTO updateCourse(CourseDTO course) throws EntityValidationException;

    void deleteCourseById(Long id);

}
