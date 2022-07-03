package com.epam.committee.dao.impl;

import com.epam.committee.dao.ApplicantDao;
import com.epam.committee.dao.BaseDao;
import com.epam.committee.dao.TableColumn;
import com.epam.committee.entity.Applicant;
import com.epam.committee.entity.ApplicantState;
import com.epam.committee.entity.Faculty;
import com.epam.committee.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class ApplicantDaoImpl extends BaseDao<Long, Applicant> implements ApplicantDao {
    private final static Logger logger = LogManager.getLogger();
    private static final String SQL_INSERT_APPLICANT = "INSERT INTO applicant (fk_user_id, fk_faculty_id, fk_applicant_state_id, fk_enrollment_id) VALUES (?, ?, ?, ?)";



    private static final String SQL_FIND_APPLICANTS_BY_USER_ID = "SELECT applicant.applicant_id as fk_applicant_id, enrollment.enrollment_id ,faculty.name, applicant_state.state_type FROM committee_db.applicant JOIN committee_db.applicant_state ON applicant.fk_applicant_state_id=applicant_state.applicant_state_id JOIN committee_db.enrollment ON applicant.fk_enrollment_id=enrollment.enrollment_id JOIN committee_db.faculty ON applicant.fk_faculty_id=faculty.faculty_id WHERE fk_user_id=?";
    //private static final String FIND_APPLICANTS_BY_USER_ID = "    SELECT applicant.id as applicant_id,              enrollment.id ,           faculty.name,applicant_state.state_type FROM admissions_committee.applicant JOIN admissions_committee.applicant_state ON applicant.applicant_state_id=applicant_state.id JOIN admissions_committee.enrollment ON applicant.enrollment_id=enrollment.id JOIN admissions_committee.faculty ON applicant.faculty_id=faculty.id WHERE user_id=?";









    private static final String SQL_FIND_LATEST_APPLICANTS = "SELECT applicant.applicant_id as fk_applicant_id, faculty.faculty_id as fk_faculty_id, faculty.name, faculty.capacity," +
            " (SELECT sum(grade) from committee_db.subject_grades WHERE fk_applicant_id=applicant.applicant_id) as total_rating FROM committee_db.applicant " +
            "JOIN committee_db.user ON applicant.fk_user_id=user.user_id JOIN committee_db.faculty ON applicant.fk_faculty_id=faculty.faculty_id " +
            "JOIN committee_db.enrollment ON applicant.fk_enrollment_id=enrollment.enrollment_id where enrollment.enrollment_id = (select max(enrollment_id) from enrollment)";





    private static final String SQL_UPDATE_ENROLLED_APPLICANT_STATE = "UPDATE applicant SET fk_applicant_state_id=2 WHERE applicant_id=?";
    private static final String SQL_UPDATE_NOT_ENROLLED_APPLICANTS_STATE = "UPDATE applicant SET fk_applicant_state_id=3 WHERE fK_applicant_state_id=1";
    private static final String SQL_FIND_APPLICANTS_BY_ENROLLMENT = "SELECT applicant.applicant_id as fk_applicant_id,user.last_name, user.first_name,user.patronymic, faculty.name," +
            "(SELECT sum(grade) from committee_db.subject_grades WHERE fk_applicant_id=applicant.applicant_id) as total_rating, applicant_state.state_type FROM committee_db.applicant" +
            " JOIN committee_db.user ON applicant.fk_user_id=user.user_id JOIN committee_db.faculty ON applicant.fk_faculty_id=faculty.faculty_id" +
            " JOIN committee_db.enrollment ON applicant.fk_enrollment_id=enrollment.enrollment_id JOIN applicant_state ON applicant_state.applicant_state_id=applicant.fk_applicant_state_id WHERE enrollment.enrollment_id = ";
    private static final String SQL_SELECT_LATEST_ENROLLMENT_ID_SUBQUERY = "(SELECT MAX(enrollment_id) FROM enrollment) AND end_date is NULL";
    private static final String SQL_DELETE_APPLICANT_BY_ID = "DELETE FROM committee_db.applicant WHERE applicant_id=?";
    private static final String SQL_FIND_APPLICANTS_ID_BY_FACULTY_ID = "SELECT applicant.applicant_id, faculty.name FROM committee_db.applicant JOIN committee_db.faculty ON applicant.fk_faculty_id=faculty.faculty_id WHERE applicant.fk_faculty_id=?";
    private static final String SQL_FIND_APPLICANT_BY_ID = "SELECT applicant.fk_enrollment_id, user.user_id, user.last_name, user.first_name, user.patronymic, faculty.name, applicant_state.state_type FROM committee_db.applicant JOIN committee_db.user ON applicant.fk_user_id=user.user_id JOIN committee_db.faculty ON applicant.fk_faculty_id=faculty.faculty_id JOIN committee_db.applicant_state ON applicant.fk_applicant_state_id=applicant_state.applicant_state_id WHERE applicant.applicant_id=?";
    private static final char PREPARED_CHAR = '?';
    private static ApplicantDaoImpl instance = new ApplicantDaoImpl();

    private ApplicantDaoImpl() {
    }

    public static ApplicantDaoImpl getInstance() {
        return instance;
    }

    public TreeSet<Applicant> getApplicantsByEnrollment(Optional<Integer> enrollmentId) throws DaoException {
 //       TreeSet<Applicant> applicants = new TreeSet<>();
        PreparedStatement statement = null;
        StringBuilder sqlQuery = new StringBuilder(SQL_FIND_APPLICANTS_BY_ENROLLMENT);
        try {
            ResultSet resultSet;
            if (enrollmentId.isPresent()) {
                sqlQuery.append(PREPARED_CHAR);
                statement = connection.prepareStatement(sqlQuery.toString());
                statement.setInt(1, enrollmentId.get());
                resultSet = statement.executeQuery();
            } else {
                sqlQuery.append(SQL_SELECT_LATEST_ENROLLMENT_ID_SUBQUERY);
                statement = connection.prepareStatement(sqlQuery.toString());
                resultSet = statement.executeQuery();
            }
            TreeSet<Applicant> applicants = new TreeSet<>();
            Applicant applicant;
            while (resultSet.next()) {
                applicant = new Applicant();
                applicant.setId(resultSet.getInt(TableColumn.FK_APPLICANT_ID));
                applicant.setLastName(resultSet.getString(TableColumn.USER_LAST_NAME));
                applicant.setFirstName(resultSet.getString(TableColumn.USER_FIRST_NAME));
                applicant.setPatronymic(resultSet.getString(TableColumn.USER_PATRONYMIC));
                applicant.setFacultyName(resultSet.getString(TableColumn.FACULTY_NAME));
                applicant.setTotalRating(resultSet.getInt(TableColumn.TOTAL_RATING));
                applicant.setApplicantState(ApplicantState.valueOf(resultSet.getString(TableColumn.STATE_TYPE).toUpperCase()));
                applicants.add(applicant);
            }
            return applicants;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not get applicants by enrollment: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    public void updateEnrolledApplicantsState(List<Integer> idList) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_ENROLLED_APPLICANT_STATE);
            for (int id : idList) {
                statement.setInt(1, id);
                statement.execute();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not update enrolled applicants state: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    public void updateNotEnrolledApplicantsState() throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_NOT_ENROLLED_APPLICANTS_STATE);
            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not update not enrolled applicants state: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    public Map<Faculty, TreeSet<Applicant>> getCurrentEnrollmentApplicants() throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_LATEST_APPLICANTS);
            ResultSet resultSet = statement.executeQuery();
            Map<Faculty, TreeSet<Applicant>> applicants = new HashMap<>();
            Faculty faculty;
            Applicant applicant;
            while (resultSet.next()) {
                faculty = new Faculty();
                faculty.setFacultyId(resultSet.getInt(TableColumn.FACULTY_ID));
                faculty.setCapacity(resultSet.getInt(TableColumn.FACULTY_CAPACITY));

                applicant = new Applicant();
                applicant.setId(resultSet.getInt(TableColumn.FK_APPLICANT_ID));
                applicant.setTotalRating(resultSet.getInt(TableColumn.TOTAL_RATING));

                if (applicants.containsKey(faculty)) {
                    applicants.get(faculty).add(applicant);
                } else {
                    applicants.put(faculty, new TreeSet<>());
                    applicants.get(faculty).add(applicant);
                }
            }
            return applicants;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not get current enrollment applicants: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    public int insert(Applicant applicant) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_APPLICANT, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, applicant.getId());
            statement.setInt(2, applicant.getFacultyId());
            statement.setInt(3, applicant.getApplicantState().getOrdinalNumber());
            statement.setInt(4, applicant.getEnrollmentId());
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            int id = 0;
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not insert applicant: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    public List<Applicant> getApplicantsByUserId(int userId) throws DaoException {
        return findApplicantsByUserId(userId);
    }

    private List<Applicant> findApplicantsByUserId(int userId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_APPLICANTS_BY_USER_ID);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Applicant> applicants = new ArrayList<>();
            Applicant applicant;
            while (resultSet.next()) {
                applicant = new Applicant();
                applicant.setUserId((long) userId);
                applicant.setId(resultSet.getInt(TableColumn.FK_APPLICANT_ID));
                applicant.setEnrollmentId(resultSet.getInt(TableColumn.ENROLLMENT_ID));
                applicant.setFacultyName(resultSet.getString(TableColumn.FACULTY_NAME));
                applicant.setApplicantState(ApplicantState.valueOf(resultSet.getString(TableColumn.STATE_TYPE).toUpperCase()));
                applicants.add(applicant);
                logger.log(Level.INFO, "get applicants by user id: ");
            }
            return applicants;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not get applicants by user id: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    public Applicant getApplicantById(int applicantId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_APPLICANT_BY_ID);
            statement.setInt(1, applicantId);
            ResultSet resultSet = statement.executeQuery();
            Applicant applicant = null;
            if (resultSet.next()) {
                applicant = new Applicant();
                applicant.setUserId(resultSet.getInt(TableColumn.USER_ID));
                applicant.setId(applicantId);
                applicant.setEnrollmentId(resultSet.getInt(TableColumn.FK_ENROLLMENT_ID));
                applicant.setLastName(resultSet.getString(TableColumn.USER_LAST_NAME));
                applicant.setFirstName(resultSet.getString(TableColumn.USER_FIRST_NAME));
                applicant.setPatronymic(resultSet.getString(TableColumn.USER_PATRONYMIC));
                applicant.setFacultyName(resultSet.getString(TableColumn.FACULTY_NAME));
                applicant.setApplicantState(ApplicantState.valueOf(resultSet.getString(TableColumn.STATE_TYPE).toUpperCase()));
            }
            return applicant;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not get applicants by id: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    public void deleteApplicantById(int applicantId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_APPLICANT_BY_ID);
            statement.setInt(1, applicantId);
            statement.execute();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not delete applicant by id: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }


    public List<Applicant> getApplicantsIdByFacultyId(int facultyId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_APPLICANTS_ID_BY_FACULTY_ID);
            statement.setInt(1, facultyId);
            ResultSet resultSet = statement.executeQuery();
            List<Applicant> applicants = new ArrayList<>();
            Applicant applicant;
            while (resultSet.next()) {
                applicant = new Applicant();
                applicant.setId(resultSet.getInt(TableColumn.APPLICANT_ID));
                applicant.setFacultyName(resultSet.getString(TableColumn.FACULTY_NAME));
                applicants.add(applicant);
            }
            return applicants;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not get applicants id by faculty id: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public List<Applicant> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Applicant> findById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean create(Applicant entity) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Applicant entity) throws DaoException {
        return false;
    }
}
