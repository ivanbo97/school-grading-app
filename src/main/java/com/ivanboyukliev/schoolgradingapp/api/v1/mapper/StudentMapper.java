package com.ivanboyukliev.schoolgradingapp.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.ivanboyukliev.schoolgradingapp.api.v1.model.StudentDTO;
import com.ivanboyukliev.schoolgradingapp.domain.Student;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;

@Mapper
public interface StudentMapper {
	
	StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
	
	default StudentDTO studentToStudentDTO(Student student) {
		return StudentDTO.builder()
				.name(student.getName())
				.studentUrl(STUDENT_BASE_URL + "/" + student.getId())
				.build();
	}
	
	@Mapping(source = "name", target = "name")
	Student studentDTOToStudent(StudentDTO studentDTO);

}
