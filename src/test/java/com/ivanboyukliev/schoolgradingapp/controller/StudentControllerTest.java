package com.ivanboyukliev.schoolgradingapp.controller;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentListDTO;
import com.ivanboyukliev.schoolgradingapp.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void findAllStudents() throws Exception {
        // given
        StudentDTO exampleStudent = StudentDTO.builder()
                .name("Jimmy Hill")
                .studentUrl("/api/v1/student/16")
                .build();
        List<StudentDTO> students = new ArrayList<>();
        students.add(exampleStudent);
        StudentListDTO studentListDTO = new StudentListDTO(students);
        given(studentService.findAllStudent()).willReturn(studentListDTO);

        mockMvc.perform(get("/api/v1/student").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students", hasSize(1)))
                .andExpect(jsonPath("$.students[0].student_name", is(exampleStudent.getName())));
    }

    @Test
    void findStudentById() throws Exception {
        //given
        StudentDTO foundStudent = new StudentDTO("Maria Petrova", "/api/v1/student/777");
        given(studentService.findStudentById(anyLong())).willReturn(foundStudent);

        mockMvc.perform(get("/api/v1/student/77")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student_name", is(foundStudent.getName())));
        then(studentService).should().findStudentById(anyLong());
    }
}