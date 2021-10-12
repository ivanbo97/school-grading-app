package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.mapper.MarkMapper;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkListDTO;
import com.ivanboyukliev.schoolgradingapp.domain.Course;
import com.ivanboyukliev.schoolgradingapp.domain.Mark;
import com.ivanboyukliev.schoolgradingapp.domain.Student;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.repository.CourseRepository;
import com.ivanboyukliev.schoolgradingapp.repository.MarkRepository;
import com.ivanboyukliev.schoolgradingapp.repository.StudentRepository;
import com.ivanboyukliev.schoolgradingapp.validation.BaseNamedEntityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.ENTITY_MARK_DATE_TIME_FORMAT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MarkServiceImplTest {

    @Mock
    private MarkRepository markRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private BaseNamedEntityValidator entityValidator;

    private MarkService markService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        markService = new MarkServiceImpl(markRepository, studentRepository, courseRepository,
                MarkMapper.INSTANCE, entityValidator);
    }

    @Test
    void findAllStudentsTest() {
        // given
        List<Mark> marks = new ArrayList<>();
        Course course = new Course(1L, "Biology");
        Student student = new Student(1L, "Yoan Doe", new HashSet<>());
        marks.add(new Mark(1L, 5.00d, LocalDateTime.now(), student, course));
        marks.add(new Mark(2L, 4.00d, LocalDateTime.now(), student, course));
        marks.add(new Mark(3L, 6.00d, LocalDateTime.now(), student, course));

        given(markRepository.findAll()).willReturn(marks);

        // when
        MarkListDTO retrievedMarks = markService.findAllMarks();

        // then
        assertEquals(3, retrievedMarks.getMarks().size());
    }

    @Test
    void findMarkByIdTest() {
        // given
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ENTITY_MARK_DATE_TIME_FORMAT);
        Course course = new Course(1L, "TestCourseOne");
        Student student = new Student(1L, "TestStudentOne", new HashSet<>());
        Mark retrievedMark = new Mark(13L, 5.00d, LocalDateTime.now(), student, course);

        given(markRepository.findById(anyLong())).willReturn(Optional.of(retrievedMark));

        // when
        MarkDTO foundMark = markService.findMarkById(13L);

        // then
        assertAll("Perform multiple assumptions on MarkDTO",
                () -> assertNotNull(foundMark),
                () -> assertEquals(course.getName(), foundMark.getCourseName()),
                () -> assertEquals(student.getName(), foundMark.getStudentName()),
                () -> assertEquals(retrievedMark.getMark(), foundMark.getMark()),
                () -> assertEquals(retrievedMark.getMarkDate().format(dtf), foundMark.getDate())
        );
        then(markRepository).should().findById(13L);
    }

    @Test
    void updateMarkTest() throws EntityValidationException {

        // given
        Student student = new Student(12L, "Ivan Doe", new HashSet<>());
        Course course = new Course(1L, "Biology");
        Mark retrievedMark = new Mark(13L, 5.0d, LocalDateTime.now(), student, course);

        MarkDTO markForUpdate = MarkDTO.builder().
                mark(4.0)
                .courseName("Biology")
                .studentName("Ivan Doe")
                .build();

        given(markRepository.findById(anyLong())).willReturn(Optional.of(retrievedMark));
        given(markRepository.save(any(Mark.class))).willReturn(retrievedMark);
        // when

        MarkDTO updatedMark = markService.updateMark(13L, markForUpdate);

        // then
        assertAll("Perform multiple assertions on MarkDTO",
                () -> assertNotNull(updatedMark),
                () -> assertEquals(retrievedMark.getCourse().getName(), updatedMark.getCourseName()),
                () -> assertEquals(retrievedMark.getStudent().getName(), updatedMark.getStudentName()),
                () -> assertEquals(retrievedMark.getCourse().getName(), updatedMark.getCourseName()));
        then(markRepository).should().findById(13L);
        then(markRepository).should().save(retrievedMark);
    }
}