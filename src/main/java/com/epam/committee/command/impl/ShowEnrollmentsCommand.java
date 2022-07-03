package com.epam.committee.command.impl;

import com.epam.committee.command.Command;
import com.epam.committee.command.ConstantName;
import com.epam.committee.command.RequestContent;
import com.epam.committee.command.Router;
import com.epam.committee.entity.Enrollment;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.EnrollmentServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowEnrollmentsCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Gets enrollment from the database,
     * sets the session attribute to show them and
     * returns router to the show faculty page.
     *
     * @param content an {@link RequestContent} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     */
    @Override
    public Router execute(RequestContent content) {
        Router router = new Router();
        EnrollmentServiceImpl enrollmentService = EnrollmentServiceImpl.getInstance();
        try {
            List<Enrollment> enrollments = enrollmentService.getAllClosedEnrollments();
            if (!enrollments.isEmpty() && enrollments != null) {
                logger.info("enrollments were found and set as attribute");
                content.addSessionAttribute(ConstantName.ATTRIBUTE_ENROLLMENTS, enrollments);
            } else {
                logger.warn("enrollments weren't found");
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            content.addSessionAttribute(ConstantName.ATTRIBUTE_ERROR,
                    MessageManager.getProperty(ConstantName.MESSAGE_ENROLLMENT_ERROR));
        }
        router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_SHOW_ENROLLMENTS));
        return router;
    }
}
