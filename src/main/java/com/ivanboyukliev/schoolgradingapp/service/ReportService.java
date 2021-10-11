package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.report.ReportDTO;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;

public interface ReportService {

    ReportDTO avgStudentMarkForCourse(Long studentId, Long courseId) throws EntityValidationException;

    ReportDTO avgStudentMarkAllCourses(Long studentId) throws EntityValidationException;

    ReportDTO avgMarkForACourse(Long courseId) throws EntityValidationException;

    ReportDTO avgMarkAllStudentsForAllCourses();

    ReportDTO avgMarkForCombinationsForStudentAndCourse();

}
