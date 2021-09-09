package com.ivanboyukliev.schoolgradingapp.util;

public final class ApplicationConstants {
	public static final String ENTITY_STUDENT_TABLE_NAME = "student";
	public static final String ENTITY_STUDENT_ID_COLUMN = "student_id";
	public static final String ENTITY_STUDENT_NAME_COLUMN = "student_name";

	public static final String ENTITY_MARK_TABLE_NAME = "mark";
	public static final String ENTITY_MARK_ID_COLUMN = "mark_id";
	public static final String ENTITY_MARK_VAL_COLUMN = "mark";
	public static final String ENTITY_MARK_DATE_COLUMN = "mark_date";
	public static final String ENTITY_MARK_STUDENT_COLUMN = "id_student";
	public static final String ENTITY_MARK_COURSE_COLUMN = "id_course";
    public static final String ENTITY_MARK_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	
	public static final String ENTITY_COURSE_TABLE_NAME = "course";
	public static final String ENTITY_COURSE_ID_COLUMN = "course_id";
	public static final String ENTITY_COURSE_NAME_COLUMN = "course_name";
	
    //== CSV header names constants ==
    public static final String CSV_HEADER_COURSE_ID = "course_id";
    public static final String CSV_HEADER_COURSE_NAME = "course_name";

    public static final String CSV_HEADER_STUDENT_ID = "student_id";
    public static final String CSV_HEADER_STUDENT_NAME = "student_name";

    public static final String CSV_HEADER_MARK_ID = "mark_id";
    public static final String CSV_HEADER_MARK = "mark";
    public static final String CSV_HEADER_MARK_DATE = "mark_date";


}
