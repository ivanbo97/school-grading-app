package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.mapper.StudentMapper;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentDTO;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentListDTO;
import com.ivanboyukliev.schoolgradingapp.domain.Student;
import com.ivanboyukliev.schoolgradingapp.exception.EntityNotFoundCustomException;
import com.ivanboyukliev.schoolgradingapp.exception.EntityValidationException;
import com.ivanboyukliev.schoolgradingapp.repository.StudentRepository;
import com.ivanboyukliev.schoolgradingapp.validation.BaseNamedEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.ERROR_STUDENT_EXISTS;
import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.ERROR_STUDENT_NOT_FOUND;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final BaseNamedEntityValidator basedNamedEntityValidator;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              StudentMapper studentMapper,
                              BaseNamedEntityValidator entityValidator) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.basedNamedEntityValidator = entityValidator;
    }

    @Override
    public StudentListDTO findAllStudent() {
        return new StudentListDTO(
                this.studentRepository.findAll()
                        .stream()
                        .map(studentMapper::studentToStudentDTO)
                        .collect(Collectors.toList()));

    }

    @Override
    public StudentDTO findStudentById(Long id) {
        return this.studentRepository.findById(id)
                .map(studentMapper::studentToStudentDTO)
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        String.format(ERROR_STUDENT_NOT_FOUND, id)));
    }

    @Override
    public StudentDTO saveStudent(StudentDTO student) throws EntityValidationException {
        this.basedNamedEntityValidator.validate(student);
        this.validateStudentExistence(student);
        return this.saveStudentToDatabase(studentMapper.studentDTOToStudent(student));
    }

    private StudentDTO saveStudentToDatabase(Student student) {
        Student savedStudent = this.studentRepository.save(student);
        System.out.println("*******SAVED STUDENT" + savedStudent.getId());
        return this.studentMapper.studentToStudentDTO(savedStudent);
    }

    private void validateStudentExistence(StudentDTO student) throws EntityValidationException {
        Optional<Student> searchedStudent = this.studentRepository.findStudentByName(student.getName());
        if (searchedStudent.isPresent()) {
            throw new EntityValidationException(String.format(ERROR_STUDENT_EXISTS, student.getName()));
        }
    }
}
