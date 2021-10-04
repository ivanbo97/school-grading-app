package com.ivanboyukliev.schoolgradingapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        given(studentService.findAllStudents()).willReturn(studentListDTO);

        mockMvc.perform(get("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON))
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

    @Test
    void updateStudentTest() throws Exception {
        StudentDTO providedStudentForUpdate = StudentDTO.builder()
                .name("Ivan Petrov")
                .studentUrl("/api/v1/student/13")
                .build();

        StudentDTO updatedStudent = StudentDTO.builder()
                .name("Ivan Petrov")
                .studentUrl("/api/v1/student/13")
                .build();

        given(studentService.updateStudent(eq(13L), any(StudentDTO.class))).willReturn(updatedStudent);

        mockMvc.perform(put("/api/v1/student/13")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(providedStudentForUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.student_name", is(providedStudentForUpdate.getName())));
    }

    @Test
    void saveStudentTest() throws Exception {
        // given
        StudentDTO providedStudent = StudentDTO.builder()
                .name("Georgi Georgiev")
                .build();

        StudentDTO savedStudent = StudentDTO.builder()
                .name("Georgi Georgiev")
                .studentUrl("/api/v1/student/13")
                .build();

        given(studentService.saveStudent(any(StudentDTO.class)))
                .willReturn(savedStudent);

        // when then
        mockMvc.perform(post("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(providedStudent)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.student_name", is(savedStudent.getName())));

    }

    @Test
    void deleteStudentByIdTest() throws Exception {
        // given
        mockMvc.perform(delete("/api/v1/student/13"))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}