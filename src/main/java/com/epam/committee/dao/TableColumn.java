package com.epam.committee.dao;

public  class TableColumn {



    private TableColumn(){
    }
    public static final String FK_APPLICANT_ID = "fk_applicant_id";
    public static final String APPLICANT_ID = "applicant_id";
    public static final String STATE_TYPE = "state_type";
    public static final String TOTAL_RATING = "total_rating";
    public static final String ENROLLMENT_TYPE = "enrollment_type";
    public static final String ENROLLMENT_ID = "enrollment_id";
    public static final String FK_ENROLLMENT_ID = "fk_enrollment_id";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String FACULTY_CAPACITY = "capacity";
    public static final String FACULTY_NAME = "name";
    public static final String FACULTY_ID = "fk_faculty_id";
    public static final String FK_FACULTY_ID = "faculty_id";

    public static final String SUBJECT_GRADES_ID = "subject_grades_id";
    public static final String SUBJECT_GRADES_APPLICANT = "fk_applicant_id";
    public static final String SUBJECT_GRADES_SUBJECT = "fk_subject_id";
    public static final String GRADE = "grade";
    public static final String SUBJECT_ID = "subject_id";
    public static final String SUBJECT_NAME = "name";
    public final static String USER_ID = "user_id";
    public final static String USER_ROLE = "fk_user_role_id";
    public final static String USER_LOGIN = "login";
    public final static String USER_PASSWORD = "password";
    public final static String USER_FIRST_NAME = "first_name";
    public final static String USER_LAST_NAME = "last_name";
    public final static String USER_PATRONYMIC = "patronymic";
    public final static String USER_EMAIL = "email";
    public final static String USER_STATUS = "isActive";
    }