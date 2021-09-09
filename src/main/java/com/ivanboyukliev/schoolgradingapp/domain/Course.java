package com.ivanboyukliev.schoolgradingapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = ENTITY_COURSE_TABLE_NAME)
public class Course {

	@Id
	@Column(name = ENTITY_COURSE_ID_COLUMN)
	private Long id;

	@Column(name = ENTITY_COURSE_NAME_COLUMN)
	private String courseName;

}
