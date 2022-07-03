package com.epam.committee.dao;

import com.epam.committee.entity.Enrollment;
import com.epam.committee.exception.DaoException;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public interface EnrollmentDao {

    List<Enrollment> getAllClosedEnrollments() throws DaoException;
    Enrollment getLatestEnrollment() throws DaoException;
    void openNewEnrollment(Timestamp timestamp) throws DaoException;
}
