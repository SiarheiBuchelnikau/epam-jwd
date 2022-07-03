package com.epam.committee.validator;

import com.epam.committee.command.ConstantName;
import com.epam.committee.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * The {@code DataValidator} class contains methods for
 * validating an input data (data from forms).
 */
public class DataValidator {
    private final static Logger logger = LogManager.getLogger();
    /**
     * The {@code String} value that is pattern for email.
     */
    private final static String REGEX_CHECK_FOR_EMAIL = "^([\\w\\-\\.]+)@([\\w\\-\\.]+)\\.([a-zA-Z]{2,5})$"; //p{lower} не совсем корректно
    /**
     * The {@code String} value that is pattern for password.
     */

    private final static String REGEX_CHECK_FOR_PASSWORD = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[#$%!\\_\\-]).{8,40})";
    /**
     * The {@code String} value that is pattern for date and time format YYYY-MM-DD HH:MM (24 hours format).
     */
    private final static String REGEX_FOR_DATA_TIME = "^(20[0-4][0-9]|2050)[-/](0[1-9]|1[0-2])[-/](0[1-9]|[12][0-9]|3[01]).((0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$)";
    private final static String REGEX_FOR_DECIMAL_VALUE = "\\d{0,10}(\\.\\d{1,2})?";
    private final static String REGEX_FOR_INTEGER_VALUE = "\\d{1,3}";
    private final static int MIN_STRING_LENGTH = 0;
    private final static int MAX_STRING_LENGTH = 45;
    private static final String FACULTY_DATA_PATTERN = "[A-Z][a-z]{1,20}(\\s?[A-Za-z][a-z]{1,20})*";

    /**
     * Validates input email, if it matches pattern and conditions.
     *
     * @param email input value.
     * @return {@code true} if email matches pattern,
     * otherwise {@code false}
     */
    private boolean validateEmail(String email) {
        return isValidString(email) &&
                Pattern.matches(REGEX_CHECK_FOR_EMAIL, email);
    }

    /**
     * Validates input password, if it matches pattern.
     *
     * @param password password input value.
     * @return {@code true} if password matches pattern,
     * otherwise {@code false}
     */
    private boolean validatePassword(String password) {
        return isValidString(password) && Pattern.matches(REGEX_CHECK_FOR_PASSWORD, password);
    }

    /**
     * Validates input password and password confirmation, if they are equal.
     *
     * @param passwordSampleOne password input value.
     * @param passwordSampleTwo password confirmation input value.
     * @return {@code true} if password are equal,
     * otherwise {@code false}
     */
    private boolean doublePasswordCheck(String passwordSampleOne, String passwordSampleTwo) {
        return passwordSampleOne.equals(passwordSampleTwo);
    }

    /**
     * Validates input string, if it matches conditions.
     *
     * @param parameter string input value.
     * @return {@code true} if string is not null, not empty and string length is 45 symbols or less,
     * otherwise {@code false}
     */
    private boolean isValidString(String parameter) {
        return parameter != null && parameter.length() > MIN_STRING_LENGTH
                && parameter.length() <= MAX_STRING_LENGTH;
    }

    /**
     * Validates input string, if they match pattern for conditions.
     *
     * @param inputData Map containing strings input values from editing cleaning data form.
     * @return {@code true} if strings are not null, not empty and match other conditions,
     * otherwise {@code false}
     */
    public Map<String, String> validateSubjectInputData(Map<String, String> inputData) {
        if (!isValidString(inputData.get(ConstantName.PARAMETER_SUBJECT_NAME))) {
            inputData.put(ConstantName.ATTRIBUTE_SUBJECT_NAME_INCORRECT,
                    MessageManager.getProperty(ConstantName.MESSAGE_INCORRECT_STRING));
            inputData.put(ConstantName.PARAMETER_SUBJECT_NAME, ConstantName.ATTRIBUTE_EMPTY_VALUE);
        }
        return inputData;
    }

    /**
     * Validates input data for restore password form - login. Checks if the params match patterns.
     *
     * @param login login input value.
     * @return {@code true} if params match patterns, not null, otherwise {@code false}
     */
    public boolean validateLogin(String login) {
        return isValidString(login);
    }

    /**
     * Validates input data for login form - login, password. Checks if the params match patterns,
     * password and password confirmation are equal
     *
     * @param login                login input value.
     * @param password             password input value.
     * @param passwordConfirmation password confirmation input value.
     * @return {@code true} if params match patterns, not null, otherwise {@code false}
     */
    public boolean validateLoginPassword(String login, String password, String passwordConfirmation) {
        return isValidString(login) && (validatePassword(password) &&
                doublePasswordCheck(password, passwordConfirmation));
    }

    /**
     * Validates input string, if they match pattern for conditions.
     *
     * @param inputData Map containing strings input values from register user data form.
     * @return {@code true} if strings are not null, not empty and match other conditions,
     * otherwise {@code false}
     */
    public Map<String, String> validateUserInputDate(Map<String, String> inputData) {
        if (!isValidString(inputData.get(ConstantName.PARAMETER_LOGIN))) {
            inputData.put(ConstantName.PARAMETER_LOGIN, ConstantName.ATTRIBUTE_EMPTY_VALUE);
            inputData.put(ConstantName.ATTRIBUTE_LOGIN_INCORRECT,
                    MessageManager.getProperty(ConstantName.MESSAGE_INCORRECT_LOGIN));
        }
        if (!doublePasswordCheck(inputData.get(ConstantName.PARAMETER_PASSWORD),
                inputData.get(ConstantName.PARAMETER_PASSWORD_CONFIRMATION))) {
            inputData.put(ConstantName.PARAMETER_PASSWORD, ConstantName.ATTRIBUTE_EMPTY_VALUE);
            inputData.put(ConstantName.PARAMETER_PASSWORD_CONFIRMATION, ConstantName.ATTRIBUTE_EMPTY_VALUE);
            inputData.put(ConstantName.ATTRIBUTE_PASSWORD_MATCH,
                    MessageManager.getProperty(ConstantName.MESSAGE_PASSWORDS_NOT_EQUAL));
        }
        if (!validatePassword(inputData.get(ConstantName.PARAMETER_PASSWORD))) {
            inputData.put(ConstantName.PARAMETER_PASSWORD, ConstantName.ATTRIBUTE_EMPTY_VALUE);
            inputData.put(ConstantName.PARAMETER_PASSWORD_CONFIRMATION, ConstantName.ATTRIBUTE_EMPTY_VALUE);
            inputData.put(ConstantName.ATTRIBUTE_PASSWORD_INCORRECT,
                    MessageManager.getProperty(ConstantName.MESSAGE_INCORRECT_PASSWORD));
        }
        if (!isValidString(inputData.get(ConstantName.PARAMETER_FIRST_NAME))) {
            inputData.put(ConstantName.PARAMETER_FIRST_NAME, ConstantName.ATTRIBUTE_EMPTY_VALUE);
            inputData.put(ConstantName.ATTRIBUTE_FIRST_NAME_INCORRECT,
                    MessageManager.getProperty(ConstantName.MESSAGE_INCORRECT_STRING));
        }
        if (!isValidString(inputData.get(ConstantName.PARAMETER_LAST_NAME))) {
            inputData.put(ConstantName.PARAMETER_LAST_NAME, ConstantName.ATTRIBUTE_EMPTY_VALUE);
            inputData.put(ConstantName.ATTRIBUTE_LAST_NAME_INCORRECT,
                    MessageManager.getProperty(ConstantName.MESSAGE_INCORRECT_STRING));
        }
        if (!isValidString(inputData.get(ConstantName.PARAMETER_PATRONYMIC))) {
            inputData.put(ConstantName.PARAMETER_PATRONYMIC, ConstantName.ATTRIBUTE_EMPTY_VALUE);
            inputData.put(ConstantName.ATTRIBUTE_PATRONYMIC_INCORRECT,
                    MessageManager.getProperty(ConstantName.MESSAGE_INCORRECT_STRING));
        }
        if (!validateEmail(inputData.get(ConstantName.PARAMETER_EMAIL))) {
            inputData.put(ConstantName.PARAMETER_EMAIL, ConstantName.ATTRIBUTE_EMPTY_VALUE);
            inputData.put(ConstantName.ATTRIBUTE_EMAIL_INCORRECT,
                    MessageManager.getProperty(ConstantName.MESSAGE_INCORRECT_EMAIL));
        }
        return inputData;
    }


    /**
     * Validates input string, if they match pattern for conditions.
     *
     * @param inputData Map containing strings input values from editing cleaning data form.
     * @return {@code true} if strings are not null, not empty and match other conditions,
     * otherwise {@code false}
     */
    public Map<String, String> validateFacultytInputData(Map<String, String> inputData) {
        if (!isValidString(inputData.get(ConstantName.PARAMETER_FACULTY_NAME))) {
            inputData.put(ConstantName.ATTRIBUTE_FACULTY_NAME_INCORRECT,
                    MessageManager.getProperty(ConstantName.MESSAGE_INCORRECT_STRING));
            inputData.put(ConstantName.PARAMETER_FACULTY_NAME, ConstantName.ATTRIBUTE_EMPTY_VALUE);
        }
        return inputData;
    }

    public static boolean validateGrades(String[] grades) {
        for (String grade : grades) {
            try {
                int gradeInt = Integer.parseInt(grade);
                if (!(gradeInt >= 10 && gradeInt <= 100)) {
                    logger.debug(MessageManager.getProperty(ConstantName.MESSAGE_INVALID_GRADE) + " Grade: " + gradeInt);
                    return false;
                }
            } catch (NumberFormatException e) {
                logger.debug(e.getMessage() + " Grade: " + grade);
                return false;
            }
        }
        return true;
    }

    public static boolean validateFacultyName(String string) {
        if (string != null) {
            return string.matches(FACULTY_DATA_PATTERN);
        } else {
            return false;
        }
    }

    public static boolean validateSubjectName(String string) {
        return validateFacultyName(string);
    }

    public static boolean validateCapacity(String capacityString) {

        try {
            int capacity = Integer.parseInt(capacityString);
            return capacity >= 2 && capacity <= 5;
        } catch (NumberFormatException e) {
            return false;
        }

    }
}