package com.epam.committee.command.impl;

import com.epam.committee.command.*;
import com.epam.committee.entity.User;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.ApplicantServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CancelApplication implements Command {
    private final static Logger logger = LogManager.getLogger();
    /**
     * Deleta applicattion from the database,
     * returns router to the cabinet page.
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
        String applicantIdString = content.getRequestParameter(ConstantName.PARAMETER_APPLICANT_ID);
        int applicantId = Integer.parseInt(applicantIdString);

        try {
            if (applicantService.cancelApplication(applicantId, user)) {
                logger.info("application has been deleted by user: " + user);
                router.setPagePath("controller?command=go_to_cabinet");
                router.setType(RouteType.REDIRECT);
            } else {
                logger.warn("user: " + user + "trying to delete not his application");
                router.setPagePath("controller?command=go_to_cabinet");
                content.addSessionAttribute(ConstantName.ATTRIBUTE_ERROR, MessageManager.getProperty(ConstantName.MESSAGE_NO_ACCESS));
                router.setType(RouteType.REDIRECT);
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            content.addSessionAttribute(ConstantName.ATTRIBUTE_ERROR, MessageManager.getProperty(ConstantName.MESSAGE_INTERNAL_ERROR));
        }
        return router;
    }
}