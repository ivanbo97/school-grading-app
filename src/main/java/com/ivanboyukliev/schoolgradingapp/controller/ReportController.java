package com.ivanboyukliev.schoolgradingapp.controller;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.report.ReportDTO;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;

@RestController
@RequestMapping(REPORT_BASE_URL)
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping(GET_AVG_MARK_FOR_STUD_IN_COURSE)
    @ResponseStatus(HttpStatus.OK)
    ReportDTO getAvgMarkForStudentInCourse(@PathVariable String studentId,
                                           @PathVariable String courseId) throws EntityValidationException {
        return reportService.avgStudentMarkForCourse(
                Long.valueOf(studentId), Long.valueOf(courseId));
    }

    @GetMapping(GET_AVG_MARK_FOR_STUD_IN_COURSES)
    @ResponseStatus(HttpStatus.OK)
    ReportDTO getAvgMarkForStudentInAllCourses(@PathVariable String studentId) throws EntityValidationException {
        return reportService.avgStudentMarkAllCourses(Long.valueOf(studentId));
    }

    @GetMapping(GET_AVG_MARK_FOR_A_COURSE)
    @ResponseStatus(HttpStatus.OK)
    ReportDTO getAvgMarkForACourse(@PathVariable String courseId) throws EntityValidationException {
        return reportService.avgMarkForACourse(Long.valueOf(courseId));
    }

    @GetMapping(GET_AVG_MARK_FOR_ALL_COURSES)
    @ResponseStatus(HttpStatus.OK)
    ReportDTO getAvgMarkForAllStudInAllCourses() {
        return reportService.avgMarkAllStudentsForAllCourses();
    }
}
