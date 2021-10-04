package com.ivanboyukliev.schoolgradingapp.domain;

import com.ivanboyukliev.schoolgradingapp.baseentity.BaseEntity;
import com.ivanboyukliev.schoolgradingapp.baseentity.BaseNamedEntity;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = ENTITY_COURSE_TABLE_NAME)
public class Course implements BaseNamedEntity, BaseEntity {

    @Id
    @Column(name = ENTITY_COURSE_ID_COLUMN)
    @CsvBindByName(column = CSV_HEADER_COURSE_ID)
    private Long id;

    @Column(name = ENTITY_COURSE_NAME_COLUMN)
    @CsvBindByName(column = CSV_HEADER_COURSE_NAME)
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getId() {
        return id;
    }
}
