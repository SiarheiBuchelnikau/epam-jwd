package com.epam.committee.command;

public class ConstantName {

    private ConstantName() {
    }

    public final static int ZERO_VALUE = 0;

    //Parameters
    public static final String PARAMETER_REQUIRED_SUBJECTS_LIST = "subject_id";
    public static final String PARAMETER_FACULTY_CAPACITY = "capacity";
    public static final String PARAMETER_GRADE = "grade";
    public static final String PARAMETER_APPLICANT_ID = "id";
    public static final String PARAMETER_APPLICANT = "applicant";
    public static final String PARAMETER_SUBJECTS = "subjects";
    public static final String PARAMETER_ENROLLMENT_ID = "id";
    public static final String PARAMETER_APPLICANTS = "applicants";
    public static final String PARAMETER_SUBJECT_NAME = "name";
    public static final String PARAMETER_FACULTY_NAME = "name";
    public final static String PARAM_NAME_COMMAND = "command";
    public final static String PARAMETER_LOGIN = "login";
    public final static String PARAMETER_PASSWORD = "password";
    public final static String PARAMETER_PASSWORD_CONFIRMATION = "passwordConfirmation";
    public final static String PARAMETER_CURRENT_PASSWORD = "currentPassword";
    public final static String PARAMETER_NEW_PASSWORD = "newPassword";
    public final static String PARAMETER_USER = "user";
    public final static String PARAMETER_FACULTY_ID = "id";
    public final static String PARAMETER_USER_ID = "userId";
    public final static String PARAMETER_FIRST_NAME = "firstName";
    public final static String PARAMETER_LAST_NAME = "lastName";
    public final static String PARAMETER_PATRONYMIC = "patronymic";
    public final static String PARAMETER_EMAIL = "email";
    public final static String PARAMETER_IS_ACTIVE = "isActive";
    public final static String PARAMETER_PAGE_START = "pageStart";
    public final static String PARAMETER_LANGUAGE = "newLanguage";

    // attributes
    public static final String ATTRIBUTE_SUBJECT_ID_LIST = "subjectIdList";
    public static final String ATTRIBUTE_FACULTY = "faculty";
    public static final String ATTRIBUTE_ERROR = "error";
    public static final String ATTRIBUTE_ADD_ENTRANT_ERROR = "addEntrantError";
    public static final String ATTRIBUTE_ENROLLMENTS = "enrollments";
    public static final String ATTRIBUTE_ENROLLMENT = "enrollment";
    public static final String ATTRIBUTE_ENROLLMENT_ERROR = "errorNewEnrollment";
    public static final String ATTRIBUTE_DELETE_SUBJECT_ERROR = "deleteSubjectsError";
    public static final String ATTRIBUTE_DELETE_FACULTY_ERROR = "deleteFacultyError";
    public static final String ATTRIBUTE_FACULTY_LIST = "listOfFaculty";
    public static final String ATTRIBUTE_SUBJECT_LIST = "subjectList";
    public final static String ATTRIBUTE_EMPTY_VALUE = "";
    public final static String ATTRIBUTE_USER = "user";
    public final static String ATTRIBUTE_LOGIN_ERROR = "loginError";
    public static final String ATTRIBUTE_SHOW_SUBJECT_ERROR = "showSubjectsError";
    public static final String ATTRIBUTE_SHOW_FACULTY_ERROR = "showFacultiesError";
    public final static String ATTRIBUTE_REGISTRATION_ERROR = "errorRegistrationMessage";
    public final static String ATTRIBUTE_LOGIN_NOT_UNIQ = "errorNotUniqLogin";
    public static final String ATTRIBUTE_ADD_SUBJECT_ERROR = "errorAddSubject";
    public static final String ATTRIBUTE_ADD_FACULTY_ERROR = "errorAddFaculty";
    public static final String ATTRIBUTE_VALIDATE_SUBJECT_ERROR = "errorValidateSubject";
    public final static String ATTRIBUTE_VALIDATED_MAP = "map";
    public final static String ATTRIBUTE_PASSWORD_MATCH = "passwordMatch";
    public final static String ATTRIBUTE_LOGIN_INCORRECT = "incorrectLogin";
    public final static String ATTRIBUTE_PASSWORD_INCORRECT = "incorrectPassword";
    public final static String ATTRIBUTE_FIRST_NAME_INCORRECT = "incorrectFirstName";
    public final static String ATTRIBUTE_LAST_NAME_INCORRECT = "incorrectLastName";
    public final static String ATTRIBUTE_PATRONYMIC_INCORRECT = "incorrectPatronymic";
    public final static String ATTRIBUTE_EMAIL_INCORRECT = "incorrectEmail";
    public static final String ATTRIBUTE_SUBJECT_NAME_INCORRECT = "incorrectSubjectName";
    public static final String ATTRIBUTE_FACULTY_NAME_INCORRECT = "incorrectFacultyName";
    public final static String ATTR_LOCALE = "locale";
    public final static String ATTRIBUTE_USER_IS_BLOCKED_ERROR = "blockedUserError";
    public final static String ATTRIBUTE_PASSWORD_RECOVER_SUCCESS = "recoverSuccess";
    public final static String ATTRIBUTE_PASSWORD_RECOVER_NO_LOGIN = "recoverNoLogin";
    public final static String ATTRIBUTE_PASSWORD_RECOVER_ERROR = "recoverError";
    public final static String ATTRIBUTE_START = "start";
    public final static String ATTRIBUTE_PAGE_PATH = "pagePath";

    // jsp path

    public final static String JSP_INDEX = "path.page.index";
    public final static String JSP_MAIN = "path.page.main";
    public final static String JSP_LOGIN = "path.page.login";
    public final static String JSP_ADMIN_CABINET = "path.page.adminCabinet";
    public final static String JSP_CLIENT_CABINET = "path.page.clientCabinet";
    public final static String JSP_ENTRANT_CABINET = "path.page.entrantCabinet";
    public final static String JSP_REGISTRATION = "path.page.registration";
    public final static String JSP_ERROR = "path.page.error";
    public static final String JSP_ADD_ENTRANT = "path.page.addEntrant";
    public static final String JSP_SHOW_FACULTIES = "path.page.showFaculties";
    public static final String JSP_SHOW_FACULTY = "path.page.showFaculty";
    public static final String JSP_SHOW_SUBJECT = "path.page.showSubjects";
    public static final String JSP_ADD_SUBJECT = "path.page.addSubject";
    public static final String JSP_ADD_FACULTY = "path.page.addFaculty";
    public final static String JSP_PASSWORD_RECOVER = "path.page.recoverPassword";
    public static final String JSP_SHOW_APPLICATION = "path.page.showApplication";
    public static final String JSP_SHOW_APPLICANTS = "path.page.showApplicants";
    public static final String JSP_SHOW_ENROLLMENTS = "path.page.showEnrollments";
    public static final String JSP_REDIRECT_ERROR = "path.page.redirectError";
    public static final String JSP_REDIRECT_LOGIN = "path.page.redirectLogin";
    public static final String JSP_REDIRECT_SHOW_FACULTIES = "path.page.redirectShowFaculties";

    // Messages

    public static final String MESSAGE_ALREADY_APPLIED = "message.already_applied";
    public static final String MESSAGE_INVALID_GRADE = "message.invalid_grade";
    public static final String MESSAGE_WRONG_USER_ROLES = "message.wrong_user_role";
    public static final String MESSAGE_NO_ACCESS = "message.no_access";
    public static final String MESSAGE_WRONG_ENROLLMENT_STATE = "message.wrong_enrollment_state";
    public static final String MESSAGE_INTERNAL_ERROR = "message.internal_error";
    public static final String MESSAGE_ENROLLMENTS = "message.enrollments";
    public static final String MESSAGE_ENROLLMENT_ERROR = "message.enrollmentError";
    public final static String MESSAGE_LOGIN_ERROR = "message.loginError";
    public final static String MESSAGE_REGISTRATION_ERROR = "message.registrationError";
    public final static String MESSAGE_INCORRECT_LOGIN = "message.incorrectLogin";
    public final static String MESSAGE_INCORRECT_PASSWORD = "message.incorrectPassword";
    public static final String MESSAGE_INCORRECT_EMAIL = "message.incorrectEmail";
    public final static String MESSAGE_PASSWORDS_NOT_EQUAL = "message.passwordNotEqual";
    public final static String MESSAGE_INCORRECT_STRING = "message.incorrectString";
    public static final String MESSAGE_VALIDATE_SUBJECT_ERROR = "message.validateSubjectError";
    public static final String MESSAGE_SHOW_SUBJECT_ERROR = "message.showSubjectError";
    public static final String MESSAGE_SHOW_FACULTY_ERROR = "message.showFacultyError";
    public final static String MESSAGE_NOT_UNIQ_LOGIN_ERROR = "message.notUniqLogin";
    public static final String MESSAGE_ADD_SUBJECT_ERROR = "message.addToSubjectError";
    public static final String MESSAGE_ADD_FACULTY_ERROR = "message.addToFacultyError";
    public final static String MESSAGE_INCORRECT_INPUT_DATA = "message.incorrectInputData";
    public final static String MESSAGE_BLOCKED_USER_ERROR = "message.blockedUserError";
    public final static String MESSAGE_PASSWORD_RECOVER_SUCCESS = "message.recoverPasswordSuccess";
    public final static String MESSAGE_PASSWORD_RECOVER_NO_LOGIN = "message.recoverPasswordNoLogin";
    public final static String MESSAGE_PASSWORD_RECOVER_ERROR = "message.recoverPasswordError";
    public static final String MESSAGE_DELETING_SUBJECT_ERROR = "message.deleteSubjectError";
    public static final String MESSAGE_DELETING_FACULTY_ERROR = "message.deleteFacultyError";

    //email constants

    public final static String SUBJECT_PASSWORD_RECOVER = "message.changePasswordSuccess";
    public final static String SUBJECT_SUCCESSFUL_REGISTRATION = "message.registrationSuccessSubject";
    public final static String EMAIL_SUCCESSFUL_REGISTRATION = "message.registrationSuccess";
    public final static String EMAIL_PASSWORD_RECOVER = "Your temporary password = 2022-Af-7";
    public final static String EMAIL_TEMPORARY_PASSWORD = "2022-Af-7";
    public final static String REAL_EMAIL_FOR_TEST = "jj4142163@gmail.com";

    public final static String COMMAND_MAIN = "/controller?command=go_to_main";
}



