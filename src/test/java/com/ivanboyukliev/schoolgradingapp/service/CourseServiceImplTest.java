package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.mapper.CourseMapper;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseListDTO;
import com.ivanboyukliev.schoolgradingapp.domain.Course;
import com.ivanboyukliev.schoolgradingapp.exception.EntityNotFoundCustomException;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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
    void findCourseById() {
        // given
        Course retrievedCourse = new Course(13L, "Biology");
        given(courseRepository.findById(anyLong())).willReturn(Optional.of(retrievedCourse));

        // when
        CourseDTO foundCourse = courseService.findCourseById(13L);

        // then
        assertNotNull(foundCourse);
        assertEquals(retrievedCourse.getName(), foundCourse.getName());
        then(courseRepository).should().findById(anyLong());
    }

    @Test
    void findCourseByIdException() {
        // given
        Optional<Course> foundCourse = Optional.empty();
        given(courseRepository.findById(anyLong())).willReturn(foundCourse);

        // when
        assertThrows(EntityNotFoundCustomException.class, () ->
                courseService.findCourseById(13L));
        // then

    }

    @Test
    void saveCourse() throws EntityValidationException {
        // given
        CourseDTO courseForSaving = CourseDTO.builder().name("Math").build();
        Course newCourse = new Course(1L, "Math");
        given(courseRepository.save(any(Course.class))).willReturn(newCourse);

        // when
        CourseDTO savedCourse = courseService.saveCourse(courseForSaving);

        // then
        assertNotNull(savedCourse);
        assertEquals("Math", savedCourse.getName());

    }

    @Test
    void updateCourse() throws EntityValidationException {
        // given
        Course retrievedCourse = new Course(12L, "Biology");

        CourseDTO courseForUpdate = CourseDTO.builder()
                .name("Biology I")
                .build();

        Course updatedCourse = new Course(12L, "Biology I");
        given(courseRepository.findById(12L)).willReturn(Optional.of(retrievedCourse));
        given(courseRepository.save(any(Course.class))).willReturn(updatedCourse);

        // when
        CourseDTO updatedCourseDTO = courseService.updateCourse(12L, courseForUpdate);

        // then
        assertNotNull(updatedCourseDTO);
        assertEquals("Biology I", updatedCourseDTO.getName());

    }

    @Test
    void updateCourseException() {
        // given
        given(courseRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThrows(EntityNotFoundCustomException.class, () ->
                courseService.updateCourse(13L, new CourseDTO()));


    }

    @Test
    void updateCourseExceptionTest() {

        // given
        CourseDTO courseForUpdate = CourseDTO.builder()
                .name("Biology I")
                .build();

        given(courseRepository.findById(12L)).willThrow(EntityNotFoundCustomException.class);

        // when, then
        assertThrows(EntityNotFoundCustomException.class, () ->
                courseService.updateCourse(12L, courseForUpdate));
    }

    @Test
    void deleteCourseException() {
        // given
        given(courseRepository.existsById(anyLong())).willReturn(false);

        // when, then
        assertThrows(EntityNotFoundCustomException.class, () ->
                courseService.deleteCourseById(13L));
    }

    @Test
    void deleteCourse() {
        // given
        given(courseRepository.existsById(anyLong())).willReturn(true);

        // when
        courseService.deleteCourseById(13L);

        // then
        then(courseRepository).should().deleteById(anyLong());
    }
}