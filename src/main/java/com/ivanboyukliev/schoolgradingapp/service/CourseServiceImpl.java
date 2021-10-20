package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.mapper.CourseMapper;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseListDTO;
import com.ivanboyukliev.schoolgradingapp.domain.Course;
import com.ivanboyukliev.schoolgradingapp.exception.EntityNotFoundCustomException;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.repository.CourseRepository;
import com.ivanboyukliev.schoolgradingapp.validation.BaseNamedEntityValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.ERROR_COURSE_EXISTS;
import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.ERROR_COURSE_NOT_FOUND;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final BaseNamedEntityValidator entityValidator;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper, BaseNamedEntityValidator entityValidator) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.entityValidator = entityValidator;
    }

    @Override
    @PreAuthorize("hasAuthority('course:read')")
    public CourseListDTO findAllCourses() {

        log.info("CourseServiceImpl::findAllCourses");
        return new CourseListDTO(
                courseRepository.findAll()
                        .stream()
                        .map(courseMapper::courseToCourseDTO)
                        .collect(Collectors.toList()));
    }

    @Override
    @PreAuthorize("hasAuthority('course:read')")
    public CourseDTO findCourseById(Long id) {
        log.info("CourseServiceImpl::findCourseById -> id passed = {}", id);
        return courseRepository.findById(id)
                .map(courseMapper::courseToCourseDTO)
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        String.format(ERROR_COURSE_NOT_FOUND, id)));
    }

    @Override
    @PreAuthorize("hasAuthority('course:write')")
    public CourseDTO saveCourse(CourseDTO course) throws EntityValidationException {
        entityValidator.validate(course);
        validateCourseExistence(course);
        return saveCourseToDatabase(courseMapper.courseDTOToCourse(course));
    }

    @Override
    @PreAuthorize("hasAuthority('course:write')")
    public CourseDTO updateCourse(Long id, CourseDTO course) throws EntityValidationException {
        entityValidator.validate(course);
        validateCourseExistence(course);
        Course retrievedCourse = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        String.format(ERROR_COURSE_NOT_FOUND, id)
                ));
        retrievedCourse.setName(course.getName());
        return saveCourseToDatabase(retrievedCourse);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCourseById(Long id) {
            if(!courseRepository.existsById(id)){
                throw new EntityNotFoundCustomException(String.format(ERROR_COURSE_NOT_FOUND,id));
            }
            courseRepository.deleteById(id);
    }

    private void validateCourseExistence(CourseDTO courseDTO) throws EntityValidationException {
        Optional<Course> optionalCourse = this.courseRepository.findCourseByName(courseDTO.getName());
        if (optionalCourse.isPresent()) {
            throw new EntityValidationException(String.format(ERROR_COURSE_EXISTS, courseDTO.getName()));
        }
    }

    private CourseDTO saveCourseToDatabase(Course course) {
        course.setId(ThreadLocalRandom.current().nextLong());
        Course savedCourse = courseRepository.save(course);
        return courseMapper.courseToCourseDTO(savedCourse);
    }
}
