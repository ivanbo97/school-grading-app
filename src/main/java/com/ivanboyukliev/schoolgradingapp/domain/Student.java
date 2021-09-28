package com.ivanboyukliev.schoolgradingapp.domain;

import com.ivanboyukliev.schoolgradingapp.baseentity.BaseEntity;
import com.ivanboyukliev.schoolgradingapp.baseentity.BaseNamedEntity;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = ENTITY_STUDENT_TABLE_NAME)
public class Student implements BaseNamedEntity, BaseEntity {

    @Id
    @Column(name = ENTITY_STUDENT_ID_COLUMN)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CsvBindByName(column = CSV_HEADER_STUDENT_ID)
    private Long id;

    @Column(name = ENTITY_STUDENT_NAME_COLUMN)
    @CsvBindByName(column = CSV_HEADER_STUDENT_NAME)
    private String name;

    @OneToMany(
            mappedBy = ENTITY_MAPPING_STUDENT,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Mark> marks = new HashSet<>();

    @OneToOne
    @JoinColumn(name = ENTITY_SCHOOL_CREDENTIALS_COLUMN_NAME_USER_CREDENTIALS_ID)
    private SchoolSystemCredential credential;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getId() {
        return id;
    }
}
