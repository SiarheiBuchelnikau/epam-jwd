package com.epam.committee.service.impl;

import com.epam.committee.dao.EntityTransaction;
import com.epam.committee.dao.impl.ApplicantDaoImpl;
import com.epam.committee.dao.impl.EnrollmentDaoImpl;
import com.epam.committee.entity.Applicant;
import com.epam.committee.entity.Enrollment;
import com.epam.committee.entity.Faculty;
import com.epam.committee.exception.DaoException;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.EnrollmentService;
import com.google.common.annotations.VisibleForTesting;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.*;

public class EnrollmentServiceImpl implements EnrollmentService {
    private final static Logger logger = LogManager.getLogger();
    private static EnrollmentServiceImpl instance = new EnrollmentServiceImpl();

    public EnrollmentServiceImpl() {
    }

    public static EnrollmentServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Enrollment> getAllClosedEnrollments() throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        EnrollmentDaoImpl enrollmentDao = EnrollmentDaoImpl.getInstance();
        List<Enrollment> enrollmentList;
        transaction.beginNoTransaction(enrollmentDao);
        try {
            enrollmentList = enrollmentDao.getAllClosedEnrollments();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Exception while getting all enrollments", e);
            throw new ServiceException(e);
        } finally {
            transaction.endNoTransaction();
        }
        return enrollmentList;
    }

    public Enrollment getLatestEnrollment() throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        EnrollmentDaoImpl enrollmentDao = EnrollmentDaoImpl.getInstance();
        transaction.beginNoTransaction(enrollmentDao);
        try {
            return enrollmentDao.getLatestEnrollment();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Exception while getting last enrollment", e);
            throw new ServiceException(e);
        } finally {
            transaction.endNoTransaction();
        }
    }

    public void openNewEnrollment(Timestamp timestamp) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        EnrollmentDaoImpl enrollmentDao = EnrollmentDaoImpl.getInstance();
        transaction.beginNoTransaction(enrollmentDao);
        try {
            enrollmentDao.openNewEnrollment(timestamp);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Exception while getting last enrollment", e);
            throw new ServiceException(e);
        } finally {
            transaction.endNoTransaction();
        }
    }

    public void closeCurrentEnrollment(Timestamp timestamp) throws ServiceException {
        EnrollmentDaoImpl enrollmentDao = EnrollmentDaoImpl.getInstance();
        ApplicantDaoImpl applicantDao = ApplicantDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(enrollmentDao, applicantDao);
        try {
            enrollmentDao.closeCurrentEnrollment(timestamp);
            Map<Faculty, TreeSet<Applicant>> currentApplicants = applicantDao.getCurrentEnrollmentApplicants();
            if (!currentApplicants.isEmpty()) {
                List<Integer> enrolledApplicantsIdList = calculateEnrolledApplicantsId(currentApplicants);
                applicantDao.updateEnrolledApplicantsState(enrolledApplicantsIdList);
                applicantDao.updateNotEnrolledApplicantsState();
            }
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Exception while committing transaction", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    @VisibleForTesting
    public List<Integer> calculateEnrolledApplicantsId(Map<Faculty, TreeSet<Applicant>> applicants) {
        logger.log(Level.INFO, "Applicants", applicants);
        List<Integer> enrolledIdList = new ArrayList<>();
        Set<Faculty> faculties = applicants.keySet();
        logger.log(Level.INFO, "Faculties", faculties);
        int count;
        for (Faculty faculty : faculties) {
            count = 0;

            Set<Applicant> currentFacultyApplicants = applicants.get(faculty);
            logger.log(Level.INFO, "currentFacultyApplicants", currentFacultyApplicants);
            Iterator<Applicant> iterator = currentFacultyApplicants.iterator();

            while (iterator.hasNext()) {
                enrolledIdList.add(iterator.next().getId());
                if (++count == faculty.getCapacity()) {
                    break;
                }
            }

        }
        return enrolledIdList;
    }
}