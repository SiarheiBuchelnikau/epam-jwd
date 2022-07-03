package com.epam.committee.dao.impl;

import com.epam.committee.dao.BaseDao;
import com.epam.committee.dao.EnrollmentDao;
import com.epam.committee.dao.TableColumn;
import com.epam.committee.entity.Enrollment;
import com.epam.committee.entity.EnrollmentState;
import com.epam.committee.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnrollmentDaoImpl extends BaseDao<Long, Enrollment> implements EnrollmentDao {
        private final static Logger logger = LogManager.getLogger();
    private static final String SQL_FIND_LATEST_ENROLLMENT = "SELECT enrollment.enrollment_id,enrollment_type,start_date, end_date from enrollment JOIN enrollment_state ON enrollment.fk_enrollment_state_id=enrollment_state.enrollment_state_id where enrollment.enrollment_id = (select max(enrollment_id) from enrollment)";
//    private static final String FIND_LATEST_ENROLLMENT = "SELECT enrollment.id,enrollment_type,start_date, end_date from enrollment JOIN enrollment_state ON enrollment.enrollment_state_id=enrollment_state.id where enrollment.id = (select max(id) from enrollment)";

//    private static final String UPDATE_LAST_ENROLLMENT = "UPDATE enrollment Set enrollment_state_id='2', end_date=? ORDER BY id DESC LIMIT 1";

    private static final String SQL_CLOSE_ENROLLMENT = "UPDATE enrollment SET fk_enrollment_state_id='2', end_date=? ORDER BY enrollment_id DESC LIMIT 1";
    private static final String SQL_CREATE_ENROLLMENT = "INSERT INTO enrollment (fk_enrollment_state_id, start_date) VALUES ('1', ?)";
    private static final String SQL_SELECT_CLOSED_ENROLLMENTS = "SELECT enrollment_id,start_date,end_date FROM committee_db.enrollment WHERE end_date is not null";
    private static EnrollmentDaoImpl instance = new EnrollmentDaoImpl();

    private EnrollmentDaoImpl() {
    }

    public static EnrollmentDaoImpl getInstance() {
        return instance;
    }

    public List<Enrollment> getAllClosedEnrollments() throws DaoException {
        List<Enrollment> enrollments = new ArrayList<>();
        Enrollment enrollment;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_CLOSED_ENROLLMENTS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                enrollment = new Enrollment();
                enrollment.setEnrollmentId(resultSet.getInt(TableColumn.ENROLLMENT_ID));
                enrollment.setStartDate(resultSet.getTimestamp(TableColumn.START_DATE));
                enrollment.setEndDate(resultSet.getTimestamp(TableColumn.END_DATE));
                enrollments.add(enrollment);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not get al closed enrollments: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return enrollments;
    }

    public void openNewEnrollment(Timestamp timestamp) throws DaoException {
        Enrollment enrollment;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CREATE_ENROLLMENT);
            statement.setTimestamp(1, timestamp);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not open new enrollment: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    public void closeCurrentEnrollment(Timestamp timestamp) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CLOSE_ENROLLMENT);
            statement.setTimestamp(1, timestamp);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not close current enrollment: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    public Enrollment getLatestEnrollment() throws DaoException {
            return findLatestEnrollment();
    }
    private Enrollment findLatestEnrollment () throws DaoException {
//    public Enrollment getLatestEnrollment () throws DaoException {

        Enrollment enrollment = new Enrollment();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_FIND_LATEST_ENROLLMENT);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                enrollment = new Enrollment();
                enrollment.setEnrollmentId(resultSet.getInt(TableColumn.ENROLLMENT_ID));
                enrollment.setState(EnrollmentState.valueOf(resultSet.getString(TableColumn.ENROLLMENT_TYPE).toUpperCase()));
                enrollment.setStartDate(resultSet.getTimestamp(TableColumn.START_DATE));
                enrollment.setEndDate(resultSet.getTimestamp(TableColumn.END_DATE));
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not get latest enrollment: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return enrollment;
    }

    @Override
    public List<Enrollment> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Enrollment> findById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean create(Enrollment entity) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Enrollment entity) throws DaoException {
        return false;
    }
}
