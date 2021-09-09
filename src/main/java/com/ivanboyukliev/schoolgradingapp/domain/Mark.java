package com.ivanboyukliev.schoolgradingapp.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static com.ivanboyukliev.schoolgradingapp.util.ApplicationConstants.*;

import java.time.LocalDateTime;

@Entity
@Table(name = ENTITY_MARK_TABLE_NAME)

public class Mark {

	@Id
	@Column(name = ENTITY_MARK_ID_COLUMN)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = ENTITY_MARK_VAL_COLUMN)
	private Double mark;

	@Column(name = ENTITY_MARK_DATE_COLUMN)
	private LocalDateTime markDate;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = ENTITY_MARK_STUDENT_COLUMN)
	private Student student;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = ENTITY_MARK_COURSE_COLUMN)
	private Course course;

}
