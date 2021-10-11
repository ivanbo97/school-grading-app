package com.ivanboyukliev.schoolgradingapp.domain;

import com.ivanboyukliev.schoolgradingapp.baseentity.BaseEntity;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = ENTITY_MARK_TABLE_NAME)

public class Mark implements BaseEntity {

    @Id
    @Column(name = ENTITY_MARK_ID_COLUMN)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = CSV_HEADER_MARK_ID)
    private Long id;

    @Column(name = ENTITY_MARK_VAL_COLUMN)
    @CsvBindByName(column = CSV_HEADER_MARK)
    private Double mark;

    @Column(name = ENTITY_MARK_DATE_COLUMN)
    @CsvBindByName(column = CSV_HEADER_MARK_DATE)
    @CsvDate(ENTITY_MARK_DATE_TIME_FORMAT)
    private LocalDateTime markDate;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = ENTITY_MARK_STUDENT_COLUMN)
    private Student student;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = ENTITY_MARK_COURSE_COLUMN)
    private Course course;

    @Override
    public Long getId() {
        return this.id;
    }
}
