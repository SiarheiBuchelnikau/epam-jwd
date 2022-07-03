package com.epam.committee.command.impl;

import com.epam.committee.command.Command;
import com.epam.committee.command.ConstantName;
import com.epam.committee.command.RequestContent;
import com.epam.committee.command.Router;
import com.epam.committee.entity.Enrollment;
import com.epam.committee.entity.EnrollmentState;
import com.epam.committee.entity.User;
import com.epam.committee.entity.UserRole;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.EnrollmentServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import com.epam.committee.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Map;

public class CloseEnrollmentCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Close Enrollment.
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
        User user = (User) content.getSessionAttribute(ConstantName.ATTRIBUTE_USER);
        Enrollment enrollment = (Enrollment) content.getSessionAttribute(ConstantName.ATTRIBUTE_ENROLLMENT);
        if (enrollment != null && enrollment.getState() == EnrollmentState.OPENED && user.getUserRole() == UserRole.ADMIN) {
            try {
                EnrollmentServiceImpl enrollmentService = EnrollmentServiceImpl.getInstance();
                enrollmentService.closeCurrentEnrollment(new Timestamp(System.currentTimeMillis()));
                logger.info("enrollment has been closed: " + enrollment);
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADMIN_CABINET));

            } catch (ServiceException e) {
                logger.error("could not close enrollment: " + e.getMessage());
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ERROR));
            }
        } else {
            logger.info("enrollment has not been closed: " + enrollment);
            content.addRequestAttribute(ConstantName.ATTRIBUTE_ENROLLMENT_ERROR,
                    MessageManager.getProperty(ConstantName.MESSAGE_ENROLLMENT_ERROR));
            router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADMIN_CABINET));
        }
        return router;
    }
}