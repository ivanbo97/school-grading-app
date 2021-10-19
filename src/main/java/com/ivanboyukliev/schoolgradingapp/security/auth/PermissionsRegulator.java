package com.ivanboyukliev.schoolgradingapp.security.auth;

import com.ivanboyukliev.schoolgradingapp.domain.Student;
import com.ivanboyukliev.schoolgradingapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("permissionsRegulator")
public class PermissionsRegulator {

    private StudentRepository studentRepository;

    @Autowired
    public PermissionsRegulator(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean isUserAuthorizedToAccess(Authentication authentication, Long providedStudentId) {
        Optional<Student> currentStudent = studentRepository.findStudentByName(authentication.getName());
        if (currentStudent.isEmpty()) {
            return false;
        }
        return currentStudent.get().getId() == providedStudentId.longValue();
    }
}
