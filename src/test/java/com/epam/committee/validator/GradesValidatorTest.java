package com.epam.committee.validator;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class GradesValidatorTest {

    private String[] validGrades;
    private String[] notValidGrades;

    @BeforeClass
    public void setUp() {
        validGrades = new String[]{"15", "14", "62", "42"};
        notValidGrades = new String[]{"f41,140,521,ds"};
    }

    @Test
    public void testValidateGradesPositive() {
        assertTrue(DataValidator.validateGrades(validGrades));
    }

    @Test
    public void testValidateGradesNegative() {
        assertFalse(DataValidator.validateGrades(notValidGrades));
    }

}
