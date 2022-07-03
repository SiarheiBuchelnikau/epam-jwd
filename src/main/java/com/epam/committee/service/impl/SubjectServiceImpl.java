package com.epam.committee.service.impl;

import com.epam.committee.command.ConstantName;
import com.epam.committee.dao.EntityTransaction;
import com.epam.committee.dao.impl.SubjectDaoImpl;
import com.epam.committee.entity.Subject;
import com.epam.committee.exception.DaoException;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.SubjectService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class SubjectServiceImpl implements SubjectService {
    private final static Logger logger = LogManager.getLogger();
    private static SubjectServiceImpl instance = new SubjectServiceImpl();

    private SubjectServiceImpl() {
    }

    public static SubjectServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Subject> findAllSubject() throws ServiceException {
        EntityTransaction transaction = new EntityTransaction();
        SubjectDaoImpl subjectDao = SubjectDaoImpl.getInstance();
        List<Subject> subjectList;
        transaction.beginNoTransaction(subjectDao);
        try {
            subjectList = subjectDao.findAll();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Exception while getting all subjects", e);
            throw new ServiceException(e);
        } finally {
            transaction.endNoTransaction();
        }
        return subjectList;
    }

    @Override
    public boolean addSubject(Map<String, String> map) throws ServiceException {
        Subject subject = new Subject();
        subject.setName(map.get(ConstantName.PARAMETER_SUBJECT_NAME));
        SubjectDaoImpl subjectDao = SubjectDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        boolean added;
        transaction.beginNoTransaction(subjectDao);
        try {
            added = subjectDao.create(subject);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Exception while adding a subject", e);
            throw new ServiceException(e);
        } finally {
            transaction.endNoTransaction();
        }
        return added;
    }

    @Override
    public boolean deleteSubject(int id) throws ServiceException {
        Subject subject = new Subject();
        subject.setSubjectId(id);
        SubjectDaoImpl subjectDao = SubjectDaoImpl.getInstance();
        EntityTransaction transaction = new EntityTransaction();
        boolean deleted;
        transaction.beginNoTransaction(subjectDao);
        try {
            deleted = subjectDao.delete(id);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Exception while deleting a subject", e);
            throw new ServiceException(e);
        } finally {
            transaction.endNoTransaction();
        }
        return deleted;
    }
}