package com.epam.committee.dao;

import com.epam.committee.entity.Faculty;
import com.epam.committee.exception.DaoException;

import java.sql.Connection;
import java.util.List;

public interface FacultyDao {
    /**
     * Delete a Faculty defined by {@code Faculty} id
     *
     * @return boolean
     * @throws DaoException if a database access error occurs
     */
    boolean delete (int facultyId) throws DaoException;
}



