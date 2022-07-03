package com.epam.committee.service.impl;

import com.epam.committee.dao.EntityTransaction;
import com.epam.committee.dao.impl.ApplicantDaoImpl;
import com.epam.committee.dao.impl.FacultyDaoImpl;
import com.epam.committee.dao.impl.SubjectDaoImpl;
import com.epam.committee.entity.Applicant;
import com.epam.committee.entity.Faculty;
import com.epam.committee.entity.Subject;
import com.epam.committee.exception.DaoException;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.FacultyService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FacultyServiceImpl implements FacultyService {
    private final static Logger logger = LogManager.getLogger();
    private static FacultyServiceImpl instance = new FacultyServiceImpl();

    private FacultyServiceImpl() {
    }

    public static FacultyServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Faculty> findAllFaculty() throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        List<Faculty> facultyList;
        transaction.beginNoTransaction(facultyDao);
        try {
            facultyList = facultyDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Exception while getting all faculties", e);
            throw new ServiceException(e);
        } finally {
            transaction.endNoTransaction();
        }
        return facultyList;
    }

    @Override
    public void addFaculty(Faculty faculty) throws ServiceException, DaoException {
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        SubjectDaoImpl subjectDao = SubjectDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        boolean added;

        transaction.begin(facultyDao, subjectDao);
        try {
            facultyDao.create(faculty);
            int FacultyId = faculty.getFacultyId();
            logger.info("Faculty has been added Id is: " + FacultyId);
            List<Subject> subjects = faculty.getRequiredSubjects();
            logger.info("List<Subject> subjects has been added: ");
            logger.info("faculty has been created!!!!2: " + faculty);
            subjectDao.insertRequiredSubjects(FacultyId, subjects);
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            transaction.end();
        }
    }

    public boolean deleteFaculty(int facultyId) throws ServiceException, DaoException {
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        SubjectDaoImpl subjectDao = SubjectDaoImpl.getInstance();
        ApplicantDaoImpl applicantDao = ApplicantDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        boolean deleted = false;
        transaction.begin(applicantDao, subjectDao, facultyDao);
        try {
            List<Applicant> applicants = applicantDao.getApplicantsIdByFacultyId(facultyId);
            if (!applicants.isEmpty()) {
                for (Applicant applicant : applicants) {
                    subjectDao.deleteGradesByApplicantId(applicant.getId());
                    applicantDao.deleteApplicantById(applicant.getId());
                }
            }
            subjectDao.deleteRequiredSubjectsByFacultyId(facultyId);
            facultyDao.delete(facultyId);
            deleted = true;
            transaction.commit();
        } catch (DaoException e) {
            transaction.rollback();
            logger.error(e.getMessage());
            throw new DaoException(e.getMessage());
        } finally {
            transaction.end();
        }
        return deleted;
    }

    public Faculty getById(int facultyId) throws ServiceException {
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        SubjectDaoImpl subjectDao = SubjectDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        List<Faculty> facultyList;
        transaction.begin(facultyDao, subjectDao);
        Faculty faculty;
        try {
            faculty = facultyDao.findById(facultyId);
            if (faculty != null) {
                faculty.setRequiredSubjects(subjectDao.getRequiredSubjects(facultyId));
            }
            return faculty;
        } catch (DaoException e) {
            transaction.rollback();
            logger.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        } finally {
            transaction.end();
        }
    }
}