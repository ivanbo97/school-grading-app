package com.ivanboyukliev.schoolgradingapp.controller;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseListDTO;
import com.ivanboyukliev.schoolgradingapp.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllCourses() throws Exception {

        // given
        CourseDTO mathCourse = new CourseDTO("Math", "api/v1/course/1");
        CourseListDTO retrievedCourses = new CourseListDTO(Arrays.asList(mathCourse));
        given(courseService.findAllCourses()).willReturn(retrievedCourses);

        // when, then
        mockMvc.perform(get("/api/v1/course")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courses", hasSize(1)))
                .andExpect(jsonPath("$.courses[0].course_name", is(mathCourse.getName())));


    }

    @Test
    void findCourseByIdTest() throws Exception {
        // given
        CourseDTO biologyCourse = new CourseDTO("Biology", "api/ve/course/7");
        given(courseService.findCourseById(anyLong())).willReturn(biologyCourse);

        // when, then
        mockMvc.perform(get("/api/v1/course/7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.course_name", is(biologyCourse.getName())));
        then(courseService).should().findCourseById(7L);
    }
}