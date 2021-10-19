package com.ivanboyukliev.schoolgradingapp.repository;

import com.ivanboyukliev.schoolgradingapp.domain.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {

    @Query("SELECT AVG(mark) FROM Mark WHERE student.id = :studentId AND course.id = :courseId")
    Double avgMarkForAStudentInACourse(@Param("studentId") Long studentId,
                                       @Param("courseId") Long courseId);

    @Query("SELECT AVG(mark) FROM Mark WHERE student.id = :studentId")
    Double avgMarkForStudentInAllCourses(@Param("studentId") Long studentId);

    @Query("SELECT AVG(mark) FROM Mark WHERE id = :courseId ")
    Double avgMarkForAllStudentsInACourse(@Param("courseId") Long courseId);
}
