package com.epam.committee.service.impl;

import com.epam.committee.dao.EntityTransaction;
import com.epam.committee.dao.impl.ApplicantDaoImpl;
import com.epam.committee.dao.impl.EnrollmentDaoImpl;
import com.epam.committee.dao.impl.SubjectDaoImpl;
import com.epam.committee.entity.*;
import com.epam.committee.exception.DaoException;
import com.epam.committee.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

public class ApplicantServiceImpl {
    private final static Logger logger = LogManager.getLogger();
    private static ApplicantServiceImpl instance = new ApplicantServiceImpl();

    private ApplicantServiceImpl() {
    }

    public static ApplicantServiceImpl getInstance() {
        return instance;
    }

    public boolean addApplicant(Applicant newApplicant, String[] grades) throws ServiceException {
        SubjectDaoImpl subjectDao = SubjectDaoImpl.getInstance();
        ApplicantDaoImpl applicantDao = ApplicantDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(subjectDao, applicantDao);
        try {
            if (!isAlreadyApplied(newApplicant)) {
                List<Subject> requiredSubjects = subjectDao.getRequiredSubjects(newApplicant.getFacultyId());
                newApplicant.setId(applicantDao.insert(newApplicant));
                List<Grade> newApplicantGrades = convertStringsToGrades(grades, requiredSubjects, newApplicant);
                subjectDao.insertGrades(newApplicantGrades);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Exception while committing transaction", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    private List<Grade> convertStringsToGrades(String[] gradesStrings, List<Subject> requiredSubjects, Applicant newApplicant) {

        List<Grade> applicantGrades = new ArrayList<>();
        Grade grade;
        for (int i = 0; i < gradesStrings.length; i++) {
            grade = new Grade();
            grade.setGrade(Integer.parseInt(gradesStrings[i]));
            grade.setApplicantId(newApplicant.getId());
            grade.setSubjectId(requiredSubjects.get(i).getSubjectId());
            int a = grade.getGrade();
            int c = grade.getApplicantId();
            int s = grade.getSubjectId();
            logger.log(Level.INFO, "GRADE!!!!!" + a);
            logger.log(Level.INFO, "ID_ApplicantGRADE!!!!!" + c);
            logger.log(Level.INFO, "ID_SubjectGRADE!!!!!" + s);
            applicantGrades.add(grade);
        }
        return applicantGrades;
    }

    private boolean isAlreadyApplied(Applicant newApplicant) throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        ApplicantDaoImpl applicantDao = ApplicantDaoImpl.getInstance();
        transaction.beginNoTransaction(applicantDao);
        try {
            List<Applicant> applicants = applicantDao.getApplicantsByUserId((int) newApplicant.getId());
            for (Applicant applicant : applicants) {
                if (applicant.getApplicantState() == ApplicantState.APPLIED) {
                    return true;
                }
            }
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Exception while getting isReadyApplied", e);
            throw new ServiceException(e);
        } finally {
            transaction.endNoTransaction();
        }
        return false;
    }

    public boolean cancelApplication(int applicantId, User currentUser) throws ServiceException {
        SubjectDaoImpl subjectDao = SubjectDaoImpl.getInstance();
        ApplicantDaoImpl applicantDao = ApplicantDaoImpl.getInstance();
        EnrollmentDaoImpl enrollmentDao = EnrollmentDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(subjectDao, applicantDao, enrollmentDao);
        try {
            Enrollment currentEnrollment = enrollmentDao.getLatestEnrollment();
            Applicant applicant = applicantDao.getApplicantById(applicantId);
            if (currentEnrollment.getState() == EnrollmentState.OPENED && applicant.getEnrollmentId() == currentEnrollment.getEnrollmentId()
                    && applicant.getId() == currentUser.getUserId()) {
                subjectDao.deleteGradesByApplicantId(applicantId);
                applicantDao.deleteApplicantById(applicantId);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Exception while committing transaction", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    public List<Applicant> getApplicantsByUserId(int userId) throws ServiceException {
        ApplicantDaoImpl applicantDao = ApplicantDaoImpl.getInstance();
        try {
            return applicantDao.getApplicantsByUserId(userId);
        } catch (DaoException e) {
            logger.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public Applicant getApplicantById(int applicantId) throws ServiceException {
        SubjectDaoImpl subjectDao = SubjectDaoImpl.getInstance();
        ApplicantDaoImpl applicantDao = ApplicantDaoImpl.getInstance();
        EnrollmentDaoImpl enrollmentDao = EnrollmentDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        transaction.begin(subjectDao, applicantDao, enrollmentDao);
        Applicant applicant;
        try {
            applicant = applicantDao.getApplicantById(applicantId);
            if (applicant != null) {
                applicant.setSubjects(subjectDao.getSubjectGradesByApplicantId(applicantId));
            }
            return applicant;
        } catch (DaoException e) {
            transaction.rollback();
            logger.log(Level.ERROR, "Exception while committing transaction", e);
            throw new ServiceException(e);
        } finally {
            transaction.end();
        }
    }

    public TreeSet<Applicant> getCurrentApplicants() throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        ApplicantDaoImpl applicantDao = ApplicantDaoImpl.getInstance();
        transaction.beginNoTransaction(applicantDao);
        try {
            return applicantDao.getApplicantsByEnrollment(Optional.empty());
        } catch (DaoException e) {
            logger.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            transaction.endNoTransaction();
        }
    }

    public TreeSet<Applicant> getApplicantsByEnrollment(int enrollmentId) throws ServiceException {
        ApplicantDaoImpl applicantDao = ApplicantDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        transaction.beginNoTransaction(applicantDao);
        try {
            return applicantDao.getApplicantsByEnrollment(Optional.of(enrollmentId));
        } catch (DaoException e) {
            logger.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            transaction.endNoTransaction();
        }
    }
}