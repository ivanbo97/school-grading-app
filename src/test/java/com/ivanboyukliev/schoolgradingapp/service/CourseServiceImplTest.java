package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.mapper.CourseMapper;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseListDTO;
import com.ivanboyukliev.schoolgradingapp.domain.Course;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.repository.CourseRepository;
import com.ivanboyukliev.schoolgradingapp.validation.BaseNamedEntityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {


    private CourseServiceImpl courseService;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private BaseNamedEntityValidator entityValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseService = new CourseServiceImpl(courseRepository, CourseMapper.INSTANCE, entityValidator);
    }

    @Test
    void findAllCourses() {
        // given
        Course mathCourse = new Course(1L, "Math");
        Course biologyCourse = new Course(2L, "Biology");

        List<Course> courses = new ArrayList<>();
        courses.add(mathCourse);
        courses.add(biologyCourse);

        given(courseRepository.findAll())
                .willReturn(courses);

        // when
        CourseListDTO retrievedCourses = courseService.findAllCourses();

        // then
        assertEquals(2, retrievedCourses.getCourses().size());
    }

    @Test
    void saveCourse() throws EntityValidationException {
        // given
        CourseDTO courseForSaving = CourseDTO.builder().name("Math").build();
        Course newCourse = new Course(1L,"Math");
        given(courseRepository.save(any(Course.class))).willReturn(newCourse);

        // when
        CourseDTO savedCourse = courseService.saveCourse(courseForSaving);

        // then
        assertNotNull(savedCourse);
        assertEquals("Math",savedCourse.getName());

    }
}