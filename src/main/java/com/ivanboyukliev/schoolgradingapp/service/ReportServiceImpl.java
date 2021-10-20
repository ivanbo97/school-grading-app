package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.report.ReportDTO;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.repository.CourseRepository;
import com.ivanboyukliev.schoolgradingapp.repository.MarkRepository;
import com.ivanboyukliev.schoolgradingapp.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.ERROR_COURSE_NOT_FOUND;
import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.ERROR_STUDENT_NOT_FOUND;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    MarkRepository markRepository;
    CourseRepository courseRepository;
    StudentRepository studentRepository;

    @Autowired
    public ReportServiceImpl(MarkRepository markRepository, CourseRepository courseRepository, StudentRepository studentRepository) {
        this.markRepository = markRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public ReportDTO avgStudentMarkForCourse(Long studentId, Long courseId) throws EntityValidationException {

        this.validateCourseExistence(courseId);
        this.validateStudentExistence(studentId);
        Double avgMark = markRepository.avgMarkForAStudentInACourse(studentId, courseId);
        return formatReport(avgMark);
    }

    @Override
    public ReportDTO avgStudentMarkAllCourses(Long studentId) throws EntityValidationException {
        this.validateStudentExistence(studentId);
        Double avgMark = markRepository.avgMarkForStudentInAllCourses(studentId);
        return formatReport(avgMark);
    }

    @Override
    public ReportDTO avgMarkForACourse(Long courseId) throws EntityValidationException {
        validateStudentExistence(courseId);
        Double avgMark = markRepository.avgMarkForAllStudentsInACourse(courseId);
        return formatReport(avgMark);
    }

    @Override
    public ReportDTO avgMarkAllStudentsForAllCourses() {
        Double avgMark = markRepository.avgMarkForAllStudentsInAllCourses();
        return formatReport(avgMark);
    }

    @Override
    public ReportDTO avgMarkForCombinationsForStudentAndCourse() {
        return null;
    }

    //== private methods ==
    private void validateStudentExistence(Long studentId) throws EntityValidationException {
        if (!this.studentRepository.existsById(studentId)) {
            throw new EntityValidationException(String.format(ERROR_STUDENT_NOT_FOUND, studentId));
        }
    }

    private void validateCourseExistence(Long courseId) throws EntityValidationException {
        if (!this.courseRepository.existsById(courseId)) {
            throw new EntityValidationException(String.format(ERROR_COURSE_NOT_FOUND, courseId));
        }
    }

    private ReportDTO formatReport(Double result) {
        log.info("Result = {}", result);
        String formattedResult = result != null
                ? String.format("Average Result = %.2f", result)
                : "No Data Found";
        return new ReportDTO(formattedResult);
    }
}
