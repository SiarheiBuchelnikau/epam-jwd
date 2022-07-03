package com.epam.committee.dao;

import com.epam.committee.entity.Grade;
import com.epam.committee.entity.Subject;
import com.epam.committee.exception.DaoException;

import java.util.List;

public interface SubjectDao {
    List<Subject> getRequiredSubjects(int facultyId) throws DaoException;

    List<Subject> getSubjectGradesByApplicantId(int applicantId) throws DaoException;

    boolean deleteGradesByApplicantId(int applicantId) throws DaoException;

    void createGrades(List<Grade> grades) throws DaoException;

    void createdRequiredSubjects(int facultyId, List<Subject> subjects) throws
        DaoException;

    boolean delete(int subjectId) throws DaoException;

    void deleteRequiredSubjectsByFacultyId(int facultyId) throws DaoException;
//    List<Subject> getAllSubjects() throws DaoException;
//    List<Subject> getRequiredSubjects(int facultyId) throws DaoException;
//    List<Subject> getSubjectGradesByApplicantId(int applicantId) throws DaoException;
    void insertGrades(List<Grade> grades) throws DaoException;
//    void insertRequiredSubjects(int facultyId, List<Subject> subjects) throws DaoException;
//    void deleteGradesByApplicantId(int applicantId) throws DaoException;
//    void deleteRequiredSubjectsByFacultyId(int facultyId) throws DaoException;
//    boolean insert(Subject subject) throws DaoException;
//    boolean delete(int subjectId) throws DaoException;
}
