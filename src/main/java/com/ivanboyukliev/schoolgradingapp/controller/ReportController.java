package com.ivanboyukliev.schoolgradingapp.controller;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.report.ReportDTO;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.REPORT_BASE_URL;

@RestController
@RequestMapping(REPORT_BASE_URL)
public class ReportController {

    private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/avg/student/{studentId}/course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    ReportDTO getAvgMarkForStudentInCourse(@PathVariable String studentId,
                                           @PathVariable String courseId) throws EntityValidationException {
        return reportService.avgStudentMarkForCourse(
                Long.valueOf(studentId), Long.valueOf(courseId));
    }

    @GetMapping("/avg/student/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    ReportDTO getAvgMarkForStudentInAllCourses(@PathVariable String studentId) throws EntityValidationException {
        return reportService.avgStudentMarkAllCourses(Long.valueOf(studentId));
    }
}
