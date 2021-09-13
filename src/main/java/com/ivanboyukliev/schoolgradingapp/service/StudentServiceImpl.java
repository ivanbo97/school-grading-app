package com.ivanboyukliev.schoolgradingapp.service;

import com.ivanboyukliev.schoolgradingapp.api.v1.mapper.StudentMapper;
import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentListDTO;
import com.ivanboyukliev.schoolgradingapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentListDTO findAllStudent() {
        return new StudentListDTO(
                this.studentRepository.findAll()
                        .stream()
                        .map(studentMapper::studentToStudentDTO)
                        .collect(Collectors.toList())
        );
    }
}
