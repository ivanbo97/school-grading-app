package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.mapper.StudentMapper;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentListDTO;
import com.ivanboyukliev.schoolgradingapp.domain.Student;
import com.ivanboyukliev.schoolgradingapp.exception.EntityNotFoundCustomException;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.repository.StudentRepository;
import com.ivanboyukliev.schoolgradingapp.validation.BaseNamedEntityValidator;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.STUDENT_BASE_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private BaseNamedEntityValidator entityValidator;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.studentService = new StudentServiceImpl(studentRepository, StudentMapper.INSTANCE, entityValidator);
    }

    @Test
    @DisplayName("Testing findAllStudent()")
    void findAllStudent() {
        // given
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "Ivan Doe"));
        students.add(new Student(2L, "Gergana Doe"));
        when(studentRepository.findAll()).thenReturn(students);

        // when
        StudentListDTO foundStudents = studentService.findAllStudent();

        // then
        assertAll("Assertions on DTO obects",
                () -> assertNotNull(foundStudents),
                () -> assertEquals(students.size(), foundStudents.getStudents().size()));

        verify(studentRepository).findAll();
    }

    @Test
    @DisplayName("Test findStudentById")
    void findStudentById() {

        // given
        Student foundStudent = new Student();
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(foundStudent));

        // when
        StudentDTO foundDTOStudent = studentService.findStudentById(12L);

        //then
        assertNotNull(foundDTOStudent);
        verify(studentRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Test findStudentById() for exception")
    void findStudentByIdExceptionTest() {
        when(studentRepository.findById(13L)).thenThrow(EntityNotFoundCustomException.class);
        assertThrows(EntityNotFoundCustomException.class,
                () -> studentService.findStudentById(13L));

        verify(studentRepository).findById(anyLong());
    }

    @Test
    void saveStudent() throws EntityValidationException {
        // given
        StudentDTO studentForSaving = StudentDTO.builder().name("Ivan Doe").build();
        StudentDTO expectedResponseDTO = StudentDTO.builder()
                .name("Ivan Doe")
                .studentUrl(STUDENT_BASE_URL + "/" + 1).build();
        Student newStudent = new Student(1L, "Ivan Doe");
        when(studentRepository.save(any(Student.class))).thenReturn(newStudent);

        // when
        StudentDTO responseDTO = studentService.saveStudent(studentForSaving);

        // then
        assertNotNull(responseDTO);
        assertEquals(newStudent.getName(), responseDTO.getName());
        verify(studentRepository).save(any(Student.class));
    }
}