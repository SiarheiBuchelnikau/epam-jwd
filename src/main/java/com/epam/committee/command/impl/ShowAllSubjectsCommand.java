package com.epam.committee.command.impl;

import com.epam.committee.command.Command;
import com.epam.committee.command.ConstantName;
import com.epam.committee.command.RequestContent;
import com.epam.committee.command.Router;
import com.epam.committee.entity.Subject;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.SubjectServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowAllSubjectsCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Gets all subjectss from the database,
     * sets the session attribute to show them and
     * returns router to the show clients page.
     *
     * @param content an {@link RequestContent} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     */
    @Override
    public Router execute(RequestContent content) {
        Router router = new Router();
        SubjectServiceImpl subjectService = SubjectServiceImpl.getInstance();
        List<Subject> subjectList;
        try {
            subjectList = subjectService.findAllSubject();
            if (!subjectList.isEmpty()) {
                content.addSessionAttribute(ConstantName.ATTRIBUTE_SUBJECT_LIST, subjectList);
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_SHOW_SUBJECT));
            } else {
                content.addRequestAttribute(ConstantName.ATTRIBUTE_SHOW_SUBJECT_ERROR,
                        MessageManager.getProperty(ConstantName.MESSAGE_SHOW_SUBJECT_ERROR));
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADMIN_CABINET));
            }
        } catch (ServiceException e) {
            logger.error("Error while getting all subjects", e);
            router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ERROR));
        }
        return router;
    }
}