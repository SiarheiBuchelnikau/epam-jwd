package com.epam.committee.service;

import com.epam.committee.entity.Subject;
import com.epam.committee.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public interface SubjectService {

    List<Subject> findAllSubject() throws ServiceException;

    boolean addSubject(Map<String, String> map) throws ServiceException;

    boolean deleteSubject(int id) throws ServiceException;
}

