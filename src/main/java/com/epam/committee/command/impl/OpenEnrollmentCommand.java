package com.epam.committee.command.impl;

import com.epam.committee.command.*;
import com.epam.committee.entity.Enrollment;
import com.epam.committee.entity.EnrollmentState;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.EnrollmentServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import com.epam.committee.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Map;

public class OpenEnrollmentCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Open and adds new Enrollment to the database.
     *
     * @param content an {@link RequestContent} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @see DataValidator#validateSubjectInputData(Map)
     */
    @Override
    public Router execute(RequestContent content) {
        Router router = new Router();
        EnrollmentServiceImpl enrollmentService = EnrollmentServiceImpl.getInstance();
        Enrollment enrollment = (Enrollment) content.getSessionAttribute(ConstantName.ATTRIBUTE_ENROLLMENT);
        if (enrollment == null || enrollment.getState() == EnrollmentState.CLOSED) {
            try {
                enrollmentService.openNewEnrollment(new Timestamp(System.currentTimeMillis()));
                logger.info("new enrollment was opened");
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADMIN_CABINET));
                router.setType(RouteType.REDIRECT);
            } catch (ServiceException e) {
                logger.error("Error while open enrollment", e);
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ERROR));
            }
        } else {
            content.addRequestAttribute(ConstantName.ATTRIBUTE_ENROLLMENT_ERROR,
                    MessageManager.getProperty(ConstantName.MESSAGE_ENROLLMENT_ERROR));
            router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADMIN_CABINET));
        }
        return router;
    }
}