package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.mapper.MarkMapper;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.CourseDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.MarkListDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentDTO;
import com.ivanboyukliev.schoolgradingapp.domain.Course;
import com.ivanboyukliev.schoolgradingapp.domain.Mark;
import com.ivanboyukliev.schoolgradingapp.domain.Student;
import com.ivanboyukliev.schoolgradingapp.exception.EntityNotFoundCustomException;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.repository.CourseRepository;
import com.ivanboyukliev.schoolgradingapp.repository.MarkRepository;
import com.ivanboyukliev.schoolgradingapp.repository.StudentRepository;
import com.ivanboyukliev.schoolgradingapp.validation.BaseNamedEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;

@Service
public class MarkServiceImpl implements MarkService {

    private final MarkRepository markRepository;

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;

    private final MarkMapper markMapper;

    private final BaseNamedEntityValidator baseNamedEntityValidator;

    @Autowired
    public MarkServiceImpl(MarkRepository markRepository, StudentRepository studentRepository,
                           CourseRepository courseRepository, MarkMapper markMapper,
                           BaseNamedEntityValidator baseNamedEntityValidator) {
        this.markRepository = markRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.markMapper = markMapper;
        this.baseNamedEntityValidator = baseNamedEntityValidator;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MarkListDTO findAllMarks() {
        return new MarkListDTO(markRepository.findAll()
                .stream()
                .map(markMapper::markToMarkDTO)
                .collect(Collectors.toList()));
    }

    @Override
    @PreAuthorize("hasAuthority('mark:read')")
    public MarkDTO findMarkById(Long id) {
        return markRepository.findById(id)
                .map(markMapper::markToMarkDTO)
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        String.format(ERROR_MARK_NOT_FOUND, id)
                ));
    }

    @Override
    @PreAuthorize("hasAuthority('mark:write')")
    public MarkDTO saveMark(MarkDTO markDTO) throws EntityValidationException {
        verifyMarkDTO(markDTO);
        Mark mark = this.relateMark(markDTO);
        return saveMarkToDatabase(mark);
    }

    @Override
    @PreAuthorize("hasAuthority('mark:write')")
    public MarkDTO updateMark(Long id, MarkDTO markDTO) throws EntityValidationException {
        verifyMarkDTO(markDTO);
        Mark mark = markRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundCustomException(String.format(
                        ERROR_MARK_NOT_FOUND, id
                )));

        mark.setMark(markDTO.getMark());
        mark.setMarkDate(LocalDateTime.now());

        return saveMarkToDatabase(mark);
    }

    @Async
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteMarkById(Long id) {
        if (!markRepository.existsById(id)) {
            throw new EntityNotFoundCustomException(String.format(ERROR_MARK_NOT_FOUND, id));
        }
        markRepository.deleteById(id);
    }

    private void verifyMarkDTO(MarkDTO markDTO) throws EntityValidationException {
        validateMarkValue(markDTO);
        validateCourseNames(markDTO);
    }

    private void validateMarkValue(MarkDTO markDTO) throws EntityValidationException {
        if (markDTO.getMark() < 2.00d) {
            throw new EntityValidationException(ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_LESS);
        }
        if (markDTO.getMark() > 6.00d) {
            throw new EntityValidationException(ERROR_ENTITY_MARK_VALUE_IS_OUT_OF_BOUNDS_MORE);
        }
    }

    private void validateCourseNames(MarkDTO markDTO) throws EntityValidationException {
        StudentDTO studentDTO = StudentDTO.builder().name(markDTO.getStudentName()).build();
        CourseDTO courseDTO = CourseDTO.builder().name(markDTO.getCourseName()).build();
        this.baseNamedEntityValidator.validate(studentDTO);
        this.baseNamedEntityValidator.validate(courseDTO);

    }

    private Mark relateMark(MarkDTO markDTO) {
        Course markCourse = getCourse(markDTO);
        Student markStudent = getStudent(markDTO);
        Mark mark = markMapper.markDTOToMark(markDTO);
        mark.setMarkDate(LocalDateTime.now());
        mark.setCourse(markCourse);
        mark.setStudent(markStudent);
        return mark;
    }

    private Student getStudent(MarkDTO markDTO) {
        return this.studentRepository.findStudentByName(markDTO.getStudentName())
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        "Student " + markDTO.getStudentName() + " not found"));
    }

    private Course getCourse(MarkDTO markDTO) {
        return courseRepository.findCourseByName(markDTO.getCourseName())
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        "Course " + markDTO.getCourseName() + " not found"));
    }

    private MarkDTO saveMarkToDatabase(Mark mark) {
        Mark savedMark = this.markRepository.save(mark);
        return this.markMapper.markToMarkDTO(savedMark);
    }
}
