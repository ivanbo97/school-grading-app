package com.ivanboyukliev.schoolgradingapp.controller;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseListDTO;
import com.ivanboyukliev.schoolgradingapp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.COURSE_BASE_URL;

@RestController
@RequestMapping(COURSE_BASE_URL)
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CourseListDTO findAllCourses() {
        return courseService.findAllCourses();
    }
}