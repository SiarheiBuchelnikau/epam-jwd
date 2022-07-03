package com.epam.committee.command.impl;

import com.epam.committee.command.Command;
import com.epam.committee.command.ConstantName;
import com.epam.committee.command.RequestContent;
import com.epam.committee.command.Router;
import com.epam.committee.entity.Applicant;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.ApplicantServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowApplicationCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Gets applicant and subjects from the database,
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
        int applicantId = Integer.parseInt(content.getRequestParameter(ConstantName.PARAMETER_APPLICANT_ID).trim());
        logger.info("applicantID :",applicantId );
        try {
            Applicant applicant = applicantService.getApplicantById(applicantId);
            logger.info("applicant :",applicant );
            if (applicant != null) {
                content.addRequestAttribute(ConstantName.PARAMETER_APPLICANT, applicant);
                content.addRequestAttribute(ConstantName.PARAMETER_SUBJECTS, applicant.getSubjects());
                logger.info("application has been found and set as attribute");

            } else {
                logger.warn("application has not been found");
            }
            router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_SHOW_APPLICATION));
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            content.addSessionAttribute(ConstantName.ATTRIBUTE_ERROR, MessageManager.getProperty(ConstantName.MESSAGE_INTERNAL_ERROR));
        }
        return router;
    }
}