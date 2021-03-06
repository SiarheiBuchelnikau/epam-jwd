package com.epam.committee.service;

import com.epam.committee.entity.Applicant;
import com.epam.committee.entity.Faculty;
import com.epam.committee.service.impl.EnrollmentServiceImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.util.*;

public class EnrollmentTest {

    private Map<Faculty, TreeSet<Applicant>> applicantsToEnroll;
    private List<Integer> expectedEnrolledIdList;
    private EnrollmentServiceImpl enrollmentService;

    @BeforeClass
    public void setUp() {
        enrollmentService = new EnrollmentServiceImpl();
        applicantsToEnroll = new HashMap<>();

        Faculty firstFaculty = new Faculty();
        firstFaculty.setCapacity(2);

        Faculty secondFaculty = new Faculty();
        secondFaculty.setCapacity(3);

        TreeSet<Applicant> firstApplicantsSet = new TreeSet<>();
        Applicant firstApplicantOne = new Applicant();
        firstApplicantOne.setId(11);
        firstApplicantOne.setTotalRating(140);
        firstApplicantsSet.add(firstApplicantOne);

        Applicant secondApplicantOne = new Applicant();
        secondApplicantOne.setId(12);
        secondApplicantOne.setTotalRating(110);
        firstApplicantsSet.add(secondApplicantOne);

        Applicant thirdApplicantOne = new Applicant();
        thirdApplicantOne.setId(13);
        thirdApplicantOne.setTotalRating(240);
        firstApplicantsSet.add(thirdApplicantOne);


        TreeSet<Applicant> secondApplicantsSet = new TreeSet<>();
        Applicant firstApplicantTwo = new Applicant();
        firstApplicantTwo.setId(21);
        firstApplicantTwo.setTotalRating(152);
        secondApplicantsSet.add(firstApplicantTwo);

        Applicant secondApplicantTwo = new Applicant();
        secondApplicantTwo.setId(22);
        secondApplicantTwo.setTotalRating(152);
        secondApplicantsSet.add(secondApplicantTwo);

        Applicant thirdApplicantTwo = new Applicant();
        thirdApplicantTwo.setId(23);
        thirdApplicantTwo.setTotalRating(162);
        secondApplicantsSet.add(thirdApplicantTwo);

        Applicant fourthApplicantTwo = new Applicant();
        fourthApplicantTwo.setId(24);
        fourthApplicantTwo.setTotalRating(172);
        secondApplicantsSet.add(fourthApplicantTwo);

        applicantsToEnroll.put(firstFaculty, firstApplicantsSet);
        applicantsToEnroll.put(secondFaculty, secondApplicantsSet);
        expectedEnrolledIdList = Arrays.asList(24, 23, 21, 13, 11);
    }


    @Test
    public void testCalculateEnrolledApplicantsId() {
        List<Integer> actualEnrolledIdList = enrollmentService.calculateEnrolledApplicantsId(applicantsToEnroll);
        Assert.assertEquals(actualEnrolledIdList, expectedEnrolledIdList);
    }

}
