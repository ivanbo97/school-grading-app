package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.mapper.StudentMapper;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentListDTO;
import com.ivanboyukliev.schoolgradingapp.domain.Student;
import com.ivanboyukliev.schoolgradingapp.exception.EntityNotFoundCustomException;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.repository.StudentRepository;
import com.ivanboyukliev.schoolgradingapp.validation.BaseNamedEntityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;

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
        given(studentRepository.findAll()).willReturn(students);

        // when
        StudentListDTO foundStudents = studentService.findAllStudent();

        // then
        assertAll("Assertions on DTO objects",
                () -> assertNotNull(foundStudents),
                () -> assertEquals(students.size(), foundStudents.getStudents().size()));

        then(studentRepository).should().findAll();
    }

    @Test
    @DisplayName("Test findStudentById")
    void findStudentById() {

        // given
        Student foundStudent = new Student();
        given(studentRepository.findById(anyLong())).willReturn(Optional.of(foundStudent));

        // when
        StudentDTO foundDTOStudent = studentService.findStudentById(12L);

        // then
        assertNotNull(foundDTOStudent);
        then(studentRepository).should().findById(12L);
    }

    @Test
    @DisplayName("Test findStudentById() for exception")
    void findStudentByIdExceptionTest() {
        // given
        given(studentRepository.findById(13L)).willThrow(EntityNotFoundCustomException.class);

        // then
        assertThrows(EntityNotFoundCustomException.class,
                () -> studentService.findStudentById(13L));
        then(studentRepository).should().findById(anyLong());
    }

    @Test
    @DisplayName("Test saveStudent()")
    void saveStudent() throws EntityValidationException {
        // given
        StudentDTO studentForSaving = StudentDTO.builder().name("Ivan Doe").build();
        Student newStudent = new Student(1L, "Ivan Doe");
        given(studentRepository.save(any(Student.class))).willReturn(newStudent);

        // when
        StudentDTO responseDTO = studentService.saveStudent(studentForSaving);

        // then
        assertNotNull(responseDTO);
        assertEquals(newStudent.getName(), responseDTO.getName());
        then(studentRepository).should().save(any(Student.class));
    }

    @Test
    void updateStudentTest() throws EntityValidationException {
        // given
        Student foundStudent = new Student(1L, "John Doe");
        StudentDTO providedStudentDTO = new StudentDTO();
        providedStudentDTO.setName("Dancho Doe");
        given(studentRepository.findById(anyLong())).willReturn(Optional.of(foundStudent));
        given(studentRepository.save(any(Student.class))).willReturn(foundStudent);
        // when
        StudentDTO updatedStudent = studentService.updateStudent(1L, providedStudentDTO);

        // then
        assertEquals(providedStudentDTO.getName(), updatedStudent.getName());
        then(studentRepository).should().findById(anyLong());
        then(studentRepository).should().save(any(Student.class));
    }

    @Test
    @DisplayName("Update Student Exception Test")
    void updateStudentExceptionTest() {
        // given
        Optional<Student> student = Optional.empty();
        StudentDTO providedStudentDTO = new StudentDTO();
        providedStudentDTO.setName("Troublesome student");
        given(studentRepository.findById(13L)).willReturn(student);

        // when
        assertThrows(EntityNotFoundCustomException.class,
                () -> studentService.updateStudent(13L, providedStudentDTO));

        // then
        then(studentRepository).should().findById(anyLong());
    }

    @Test
    void deleteNotExistingStudentByIdTest() {
        // given
        given(studentRepository.existsById(anyLong())).willReturn(false);

        // when, then
        assertThrows(EntityNotFoundCustomException.class,
                () -> studentService.deleteStudentById(13L));
    }

    @Test
    void deleteExistingStudentByIdTest() {
        // given
        given(studentRepository.existsById(anyLong())).willReturn(true);
        // when, then
        assertDoesNotThrow(() -> studentService.deleteStudentById(anyLong()));
        then(studentRepository).should().existsById(anyLong());


    }
}