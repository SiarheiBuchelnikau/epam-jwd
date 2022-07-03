package com.epam.committee.command.impl;

import com.epam.committee.command.Command;
import com.epam.committee.command.ConstantName;
import com.epam.committee.command.RequestContent;
import com.epam.committee.command.Router;
import com.epam.committee.entity.Applicant;
import com.epam.committee.entity.User;
import com.epam.committee.entity.UserRole;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.ApplicantServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class ShowApplicantsCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Gets applicants from the database,
     * sets the request attribute to show them and
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
        ApplicantServiceImpl applicantService = ApplicantServiceImpl.getInstance();
        router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ERROR));
        User user = (User) content.getSessionAttribute(ConstantName.ATTRIBUTE_USER);
        String enrollmentId = content.getRequestParameter(ConstantName.PARAMETER_ENROLLMENT_ID);
        try {
            Set<Applicant> applicants;
            if (enrollmentId == null && user != null && user.getUserRole() == UserRole.ADMIN) {
                applicants = applicantService.getCurrentApplicants();
            } else if (enrollmentId != null) {
                applicants = applicantService.getApplicantsByEnrollment(Integer.parseInt(enrollmentId));
            } else {
                content.addSessionAttribute(ConstantName.ATTRIBUTE_ERROR, MessageManager.getProperty(ConstantName.MESSAGE_WRONG_USER_ROLES));
                logger.debug(MessageManager.getProperty(ConstantName.MESSAGE_WRONG_USER_ROLES));
                return router;
            }
            content.addRequestAttribute(ConstantName.PARAMETER_APPLICANTS, applicants);
            router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_SHOW_APPLICANTS));
            if (!applicants.isEmpty()) {
                logger.info("applicants were found and set as attribute");
            } else {
                logger.warn("applicants weren't found");
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
        return router;
    }
}
