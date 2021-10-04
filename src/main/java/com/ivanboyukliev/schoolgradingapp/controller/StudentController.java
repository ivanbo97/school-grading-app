package com.ivanboyukliev.schoolgradingapp.controller;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentListDTO;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.STUDENT_BASE_URL;

@RestController
@RequestMapping(STUDENT_BASE_URL)
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public StudentListDTO findAllStudents() {
        return this.studentService.findAllStudents();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO findStudentById(@PathVariable String id) {
        return studentService.findStudentById(Long.valueOf(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO saveStudent(@RequestBody StudentDTO receivedStudent) throws EntityValidationException {
        return this.studentService.saveStudent(receivedStudent);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(@PathVariable String id,
                                    @RequestBody StudentDTO studentDTO) throws EntityValidationException {
        return studentService.updateStudent(Long.valueOf(id), studentDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudentById(@PathVariable String id) {
        studentService.deleteStudentById(Long.valueOf(id));
    }
}
