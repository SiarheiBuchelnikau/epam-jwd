package com.epam.committee.dao.impl;

import com.epam.committee.dao.BaseDao;
import com.epam.committee.dao.FacultyDao;
import com.epam.committee.dao.TableColumn;
import com.epam.committee.entity.Faculty;
import com.epam.committee.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FacultyDaoImpl extends BaseDao<Long, Faculty> implements FacultyDao {
    private final static Logger logger = LogManager.getLogger();
    private static final String SQL_SELECT_ALL_FACULTY = "SELECT * FROM committee_db.faculty order by faculty_id";
    private static final String SQL_UPDATE_FACULTY = "UPDATE committee_db.faculty SET name= ?, capacity= ? WHERE faculty_id=?";
    private static final String SQL_CREATE_FACULTY = "INSERT INTO committee_db.faculty (faculty.name, faculty.capacity) VALUES (?,?)";
    private static final String SQL_DELETE_FACULTY_BY_ID = "DELETE FROM committee_db.faculty WHERE faculty_id=?";
    private static final String SQL_SELECT_FACULTY_BY_ID = "SELECT faculty_id, name, capacity FROM committee_db.faculty WHERE faculty_id=?";
    private static FacultyDaoImpl instance = new FacultyDaoImpl();

    private FacultyDaoImpl() {
    }

    public static FacultyDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean create(Faculty faculty) throws DaoException {
//        int id = 0;
        boolean created = false;
        PreparedStatement statement = null;
        ResultSet generatedKeys;
        try {
            statement = connection.prepareStatement(SQL_CREATE_FACULTY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, faculty.getName());
            statement.setInt(2, faculty.getCapacity());
            statement.executeUpdate();
            generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                faculty.setFacultyId(generatedKeys.getInt(1));
                created = true;
//                id = faculty.getFacultyId();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Faculty creation failed! ", e);

            throw new DaoException(e);
        } finally {
            close(statement);
        }
//        return id;
        logger.info("faculty has been created!!!!: " + faculty);
        return created;
    }

    @Override
    public boolean update(Faculty faculty) throws DaoException {
        boolean updated;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_UPDATE_FACULTY);
            statement.setString(1, faculty.getName());
            statement.setInt(2, faculty.getCapacity());
            updated = statement.executeUpdate() != 0;
            logger.log(Level.DEBUG, updated);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception (reqest or table failed): ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return updated;
    }

    @Override
    public List<Faculty> findAll() throws DaoException {
//        List<Faculty> faculties = new ArrayList<>();
        PreparedStatement statement = null;
//        Faculty faculty;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_FACULTY);
            ResultSet resultSet = statement.executeQuery();
            List<Faculty> faculties = new ArrayList<>();
            while (resultSet.next()) {
//                 faculty = extractFaculty(resultSet);
//                faculties.add(faculty);
                Faculty faculty = new Faculty();
                faculty.setFacultyId(resultSet.getInt(1));
                faculty.setName(resultSet.getString(2));
                faculty.setCapacity(resultSet.getInt(3));
                faculties.add(faculty);
//                Faculty faculty = new Faculty();
//                faculty.setId(resultSet.getLong(TableColumn.FACULTY_ID));
//                faculty.setName(resultSet.getString(TableColumn.FACULTY_NAME));
//                faculty.setCapacity(resultSet.getInt(TableColumn.FACULTY_CAPACITY));
//                faculties.add(faculty);
            }
            return faculties;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "could not get faculties: ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
//        return faculties;
    }

    @Override
    public Optional<Faculty> findById(Long id) throws DaoException {
        Faculty faculty = new Faculty();
        Optional<Faculty> facultyOptional;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_FACULTY_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                faculty = extractFaculty(resultSet);
            }
            facultyOptional = Optional.ofNullable(faculty);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception (request or table failed): ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return facultyOptional;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_FACULTY_BY_ID);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DaoException("could not delete faculty by id: " + e.getMessage());
        }finally {
            close(statement);
        }
        return true;
    }

    private Faculty extractFaculty(ResultSet resultSet) throws SQLException {
        Faculty faculty = new Faculty();
//        faculty.setFacultyId(resultSet.getLong(TableColumn.FACULTY_ID));
//        faculty.setName(resultSet.getString(TableColumn.FACULTY_NAME));
//        faculty.setCapacity(resultSet.getInt(TableColumn.FACULTY_CAPACITY));
        faculty.setFacultyId(resultSet.getInt(1));
        faculty.setName(resultSet.getString(2));
        faculty.setCapacity(resultSet.getInt(3));
        return faculty;
    }

    public Faculty findById(int id) throws DaoException {
        Faculty faculty = new Faculty();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_FACULTY_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                faculty = extractFaculty(resultSet);
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "SQL exception (request or table failed): ", e);
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return faculty;
    }
}