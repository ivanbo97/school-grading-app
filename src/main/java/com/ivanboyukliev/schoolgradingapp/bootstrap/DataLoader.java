package com.ivanboyukliev.schoolgradingapp.bootstrap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ivanboyukliev.schoolgradingapp.domain.Course;
import com.ivanboyukliev.schoolgradingapp.domain.Mark;
import com.ivanboyukliev.schoolgradingapp.domain.Student;
import com.ivanboyukliev.schoolgradingapp.repository.CourseRepository;
import com.ivanboyukliev.schoolgradingapp.repository.MarkRepository;
import com.ivanboyukliev.schoolgradingapp.repository.StudentRepository;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	private final MarkRepository markRepository;

	private final Set<Student> retrievedStudents;
	private final Set<Course> retrievedCourses;
	private final Set<Mark> retrievedMarks;

	@Autowired
	public DataLoader(StudentRepository studentRepository, CourseRepository courseRepository,
			MarkRepository markRepository) {
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.markRepository = markRepository;
		retrievedStudents = new HashSet<Student>();
		retrievedMarks = new HashSet<Mark>();
		retrievedCourses = new HashSet<Course>();
	}

	@Override
	public void run(String... args) throws Exception {

		List<AggregatedDataBean> allAggregatedData = loadDataFromFile();
		processRetrievedData(allAggregatedData);

		studentRepository.saveAll(retrievedStudents);
		courseRepository.saveAll(retrievedCourses);
		markRepository.saveAll(retrievedMarks);

		log.info("Sucessful saved data to DB.");
	}

	private List<AggregatedDataBean> loadDataFromFile() {

		List<AggregatedDataBean> allAggregatedData = new ArrayList<>();

		try (InputStream inputStream = getClass().getResourceAsStream("/marks.csv")) {
			allAggregatedData = new CsvToBeanBuilder<AggregatedDataBean>(
					new CSVReader(new BufferedReader(new InputStreamReader(inputStream))))
							.withType(AggregatedDataBean.class).build().parse();
		} catch (IOException exc) {
			log.info("Problem occured during file opening: " + exc.getMessage());
		}
		log.info("Data loading completed successfully!");
		return allAggregatedData;
	}

	private void processRetrievedData(List<AggregatedDataBean> aggregatedDataBeans) {

		for (AggregatedDataBean dataBean : aggregatedDataBeans) {
			dataBean.bindMarkToStudentAndCourse();
			retrievedStudents.add(dataBean.getStudent());
			retrievedCourses.add(dataBean.getCourse());
			retrievedMarks.add(dataBean.getMark());
		}
	}

}
