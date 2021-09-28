package com.ivanboyukliev.schoolgradingapp.util;

public final class ApplicationConstants {
    public static final String ENTITY_STUDENT_TABLE_NAME = "student";
    public static final String ENTITY_STUDENT_ID_COLUMN = "student_id";
    public static final String ENTITY_STUDENT_NAME_COLUMN = "student_name";
    public static final String ENTITY_MAPPING_STUDENT = "student";

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

    public static final String ENTITY_SCHOOL_CREDENTIALS_TABLE_NAME = "user_credentials";
    public static final String ENTITY_SCHOOL_CREDENTIALS_COLUMN_NAME_USER_CREDENTIALS_ID = "user_credentials_id";


    // == CSV header names constants ==
    public static final String CSV_HEADER_COURSE_ID = "course_id";
    public static final String CSV_HEADER_COURSE_NAME = "course_name";

    public static final String CSV_HEADER_STUDENT_ID = "student_id";
    public static final String CSV_HEADER_STUDENT_NAME = "student_name";

    public static final String CSV_HEADER_MARK_ID = "mark_id";
    public static final String CSV_HEADER_MARK = "mark";
    public static final String CSV_HEADER_MARK_DATE = "mark_date";

    // End Point Mappings
    public static final String API_V1 = "/api/v1";
    public static final String STUDENT_BASE_URL = API_V1 + "/student";

    // Exception Messages
    public static final String ERROR_STUDENT_NOT_FOUND = "Student with id = %d not found.";
    public static final String ERROR_ENTITY_NAME_IS_NULL = "Entity ID is null.";
    public static final String ERROR_ENTITY_NAME_IS_EMPTY = "Entity name is empty.";
    public static final String ERROR_ENTITY_NAME_IS_BLANK = "Entity name is blank.";

    public static final String ERROR_STUDENT_EXISTS = "Entity with name %s already exists.";
}
