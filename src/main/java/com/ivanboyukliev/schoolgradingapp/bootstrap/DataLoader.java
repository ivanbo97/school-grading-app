package com.ivanboyukliev.schoolgradingapp.bootstrap;

import com.ivanboyukliev.schoolgradingapp.domain.Course;
import com.ivanboyukliev.schoolgradingapp.domain.Mark;
import com.ivanboyukliev.schoolgradingapp.domain.SchoolSystemCredential;
import com.ivanboyukliev.schoolgradingapp.domain.Student;
import com.ivanboyukliev.schoolgradingapp.repository.CourseRepository;
import com.ivanboyukliev.schoolgradingapp.repository.MarkRepository;
import com.ivanboyukliev.schoolgradingapp.repository.StudentRepository;
import com.ivanboyukliev.schoolgradingapp.security.roles.SchoolSystemUserRole;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ivanboyukliev.schoolgradingapp.security.roles.SchoolSystemUserRole.ADMIN;
import static com.ivanboyukliev.schoolgradingapp.security.roles.SchoolSystemUserRole.USER;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final MarkRepository markRepository;

    private final Set<Student> retrievedStudents;
    private final Set<Course> retrievedCourses;
    private final Set<Mark> retrievedMarks;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(StudentRepository studentRepository,
                      CourseRepository courseRepository,
                      MarkRepository markRepository,
                      PasswordEncoder passwordEncoder) {

        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.markRepository = markRepository;
        this.passwordEncoder = passwordEncoder;
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

        log.info("Adding credentials...");
        for (long i = 1; i < 5; i++) {
            SchoolSystemUserRole role = (i % 2 != 0) ? USER : ADMIN;
            Student student = studentRepository.findById(i).orElseThrow();
            addCredentials(student, role);
        }
        log.info("Successful saved data to DB.");
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

    private void addCredentials(Student student, SchoolSystemUserRole role) {
        SchoolSystemCredential credential = SchoolSystemCredential.builder()
                .username(student.getName() + student.getId())
                .password(passwordEncoder.encode("password"))
                .student(student)
                .userRole(role)
                .build();
        student.setCredential(credential);
        studentRepository.save(student);
    }

}
