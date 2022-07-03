package com.epam.committee.service;

import com.epam.committee.entity.Enrollment;
import com.epam.committee.exception.ServiceException;

import java.util.List;

public interface EnrollmentService {
    List<Enrollment> getAllClosedEnrollments() throws ServiceException;
}
