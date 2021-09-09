package com.ivanboyukliev.schoolgradingapp.bootstrap;

import org.springframework.stereotype.Component;

import com.ivanboyukliev.schoolgradingapp.domain.Course;
import com.ivanboyukliev.schoolgradingapp.domain.Mark;
import com.ivanboyukliev.schoolgradingapp.domain.Student;
import com.opencsv.bean.CsvRecurse;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AggregatedDataBean {

	@CsvRecurse
	private Student student;

	@CsvRecurse
	private Course course;

	@CsvRecurse
	private Mark mark;

	public void bindMarkToStudentAndCourse() {
		
		mark.setStudent(student);
		mark.setCourse(course);
	}
}
