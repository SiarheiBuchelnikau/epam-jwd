package com.epam.committee.service;

import com.epam.committee.entity.Faculty;
import com.epam.committee.entity.Subject;
import com.epam.committee.exception.DaoException;
import com.epam.committee.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface FacultyService {
    List<Faculty> findAllFaculty() throws ServiceException;


    void addFaculty(Faculty faculty) throws ServiceException, DaoException;

    boolean deleteFaculty(int id) throws ServiceException, DaoException;
}
