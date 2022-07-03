package com.epam.committee.dao.impl;

import com.epam.committee.dao.*;
import com.epam.committee.entity.Grade;
import com.epam.committee.entity.Subject;
import com.epam.committee.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubjectDaoImpl extends BaseDao<Integer, Subject> implements SubjectDao {
    private final static Logger logger = LogManager.getLogger();

    private static final String FIND_SUBJECTS_BY_FACULTY_ID = "SELECT subject.subject_id, subject.name FROM subject JOIN faculty, required_subject WHERE subject.subject_id=required_subject.fk_subject_id and faculty.faculty_id=required_subject.fk_faculty_id and faculty.faculty_id=?";
    private static final String INSERT_SUBJECT_GRADES = "INSERT INTO subject_grades (fk_applicant_id, fk_subject_id, grade) VALUES (?,?,?)";
    private static final String SQL_DELETE_GRADES_BY_APPLICANT_ID = "DELETE FROM committee_db.subject_grades WHERE fk_applicant_id=?";
    private static final String SQL_FIND_ALL_SUBJECTS = "SELECT * FROM committee_db.subject order by subject_id";
    private static final String SQL_FIND_SUBJECT_GRADE_BY_APPLICANT_ID = "SELECT subject.name, subject_grades.grade FROM committee_db.subject_grades JOIN subject ON subject.subject_id=subject_grades.fk_subject_id JOIN committee_db.applicant ON subject_grades.fk_applicant_id=applicant.applicant_id WHERE applicant.applicant_id=?";
    private static final String INSERT_SUBJECT = "INSERT INTO committee_db.subject(subject.name) VALUES (?)";
    private static final String INSERT_REQUIRED_SUBJECTS = "INSERT INTO committee_db.required_subject (fk_faculty_id, fk_subject_id) VALUES(?,?)";
    private static final String DELETE_SUBJECT_BY_ID = "DELETE FROM committee_db.subject WHERE subject_id=?";
    private static final String DELETE_REQUIRED_SUBJECTS_BY_FACULTY_ID = "DELETE FROM committee_db.required_subject WHERE fk_faculty_id=?";
    private static SubjectDaoImpl instance = new SubjectDaoImpl();

    private SubjectDaoImpl() {
    }

    public static SubjectDaoImpl getInstance() {
        return instance;
    }
    @Override
    public List<Subject> findAll() throws DaoException {
        List<Subject> subjects = new ArrayList<>();
        Subject subject;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_ALL_SUBJECTS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                subject = extractSubject(resultSet);
                subjects.add(subject);
//                subject.setSubjectId(resultSet.getLong(TableColumn.SUBJECT_ID));
//                subject.setName(resultSet.getString(TableColumn.SUBJECT_NAME));
//                subjects.add(subject);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not get all subjects: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return subjects;
    }

    @Override
    public Optional<Subject> findById(Integer id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean create(Subject subject) throws DaoException {
        boolean created = false;
        PreparedStatement statement = null;
        ResultSet generatedKeys;
        try {
            statement = connection.prepareStatement(INSERT_SUBJECT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, subject.getName());
            statement.execute();
            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                subject.setSubjectId(generatedKeys.getInt(1));
                created = true;
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Subject creation failed! ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return created;
    }

    @Override
    public boolean update(Subject subject) throws DaoException {
        return false;
    }


    @Override
    public List<Subject> getRequiredSubjects(int facultyId) throws DaoException {
//        List<Subject> requiredSubjects = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(FIND_SUBJECTS_BY_FACULTY_ID);
            statement.setInt(1, facultyId);
            ResultSet resultSet = statement.executeQuery();
            List<Subject> requiredSubjects = new ArrayList<>();
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setName(resultSet.getString(TableColumn.SUBJECT_NAME));
                subject.setSubjectId(resultSet.getInt(TableColumn.SUBJECT_ID));
                requiredSubjects.add(subject);
            }
            return requiredSubjects;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception (reqest or table failed): ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
//        return requiredSubjects;
    }

    @Override
    public List<Subject> getSubjectGradesByApplicantId(int applicantId) throws DaoException {
        List<Subject> subjects = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_SUBJECT_GRADE_BY_APPLICANT_ID);
            statement.setInt(1, applicantId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setName(resultSet.getString(TableColumn.SUBJECT_NAME));
                subject.setGrade(resultSet.getInt(TableColumn.GRADE));
                subjects.add(subject);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not get subjects grades by applicant id: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
            return subjects;
        }
    }

    @Override
    public boolean deleteGradesByApplicantId(int applicantId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_GRADES_BY_APPLICANT_ID);
            statement.setInt(1, applicantId);
            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not delete subjects grades by applicant id: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }

    @Override
    public void createGrades(List<Grade> grades) throws DaoException {
        PreparedStatement statement = null;
        for (Grade grade : grades) {
            try {
                statement = connection.prepareStatement(INSERT_SUBJECT_GRADES);
                statement.setInt(1, grade.getApplicantId());
                statement.setInt(2, grade.getSubjectId());
                statement.setInt(3, grade.getGrade());
                statement.executeUpdate();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "could not insert grades ", e);
                throw new DaoException(e);
            }
        }
    }

    @Override
    public void createdRequiredSubjects(int facultyId, List<Subject> subjects) throws DaoException {
        PreparedStatement statement = null;
        for (Subject subject : subjects) {
            try {
                statement = connection.prepareStatement(INSERT_REQUIRED_SUBJECTS);
                statement.setInt(1, facultyId);
                statement.setInt(2, subject.getSubjectId());
                statement.executeUpdate();
            } catch (SQLException e) {
                logger.log(Level.ERROR, "could not insert required subjects: ", e);
                throw new DaoException(e);
            }
        }
    }
    public void insertRequiredSubjects(int facultyId, List<Subject> subjects) throws DaoException {
        for (Subject subject : subjects) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_REQUIRED_SUBJECTS);
                preparedStatement.setInt(1, facultyId);
                preparedStatement.setInt(2, subject.getSubjectId());
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new DaoException("could not insert required subjects: " + e.getMessage());
            }
        }
    }








    @Override
    public boolean delete(int subjectId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_SUBJECT_BY_ID);
            statement.setInt(1, subjectId);
            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not delete subject by id: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }

    @Override
    public void deleteRequiredSubjectsByFacultyId(int facultyId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(DELETE_REQUIRED_SUBJECTS_BY_FACULTY_ID);
            statement.setInt(1, facultyId);
            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not delete required subjects by faculty id: ", e);
            throw new DaoException(e);
        }
    }
    public void insertGrades(List<Grade> grades) throws DaoException {
        PreparedStatement statement = null;
        for (Grade grade : grades) {
            try {
                statement = connection.prepareStatement(INSERT_SUBJECT_GRADES);
                statement.setInt(1, grade.getApplicantId());
                statement.setInt(2, grade.getSubjectId());
//                statement.setInt(3, Integer.parseInt(grade.getGrade()));
                statement.setInt(3, grade.getGrade());
                statement.execute();
            } catch (SQLException e) {
                throw
                        new DaoException("could not insert grades " + e.getMessage());
            }
        }
    }


    private Subject extractSubject(ResultSet resultSet) throws SQLException {
        Subject subject = new Subject();
//        subject.setSubjectId (resultSet.getLong(TableColumn.SUBJECT_ID));
//        subject.setName(resultSet.getString(TableColumn.SUBJECT_NAME));
        subject.setSubjectId (resultSet.getInt(1));
        subject.setName(resultSet.getString(2));
        return subject;
    }
}