package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.mapper.CourseMapper;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseListDTO;
import com.ivanboyukliev.schoolgradingapp.exception.EntityNotFoundCustomException;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.repository.CourseRepository;
import com.ivanboyukliev.schoolgradingapp.validation.BaseNamedEntityValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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
    public CourseListDTO findAllCourses() {

        log.info("CourseServiceImpl::findAllCourses");
        return new CourseListDTO(
                courseRepository.findAll()
                        .stream()
                        .map(courseMapper::courseToCourseDTO)
                        .collect(Collectors.toList()));
    }

    @Override
    public CourseDTO findCourseById(Long id) {
        log.info("CourseServiceImpl::findCourseById -> id passed = {}", id);
        return courseRepository.findById(id)
                .map(courseMapper::courseToCourseDTO)
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        String.format(ERROR_COURSE_NOT_FOUND, id)));
    }

    @Override
    public CourseDTO saveCourse(CourseDTO course) throws EntityValidationException {
        return null;
    }

    @Override
    public CourseDTO updateCourse(CourseDTO course) throws EntityValidationException {
        return null;
    }

    @Override
    public void deleteCourseById(Long id) {

    }
}
