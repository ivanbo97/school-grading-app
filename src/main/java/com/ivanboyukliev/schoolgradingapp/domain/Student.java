package com.ivanboyukliev.schoolgradingapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = ENTITY_STUDENT_TABLE_NAME)
public class Student {

	@Id
	@Column(name = ENTITY_STUDENT_ID_COLUMN)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@CsvBindByName(column = CSV_HEADER_STUDENT_ID)
	private Long id;

	@Column(name = ENTITY_STUDENT_NAME_COLUMN)
	@CsvBindByName(column = CSV_HEADER_STUDENT_NAME)
	private String studentName;

}
