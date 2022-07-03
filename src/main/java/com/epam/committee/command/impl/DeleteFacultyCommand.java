package com.epam.committee.command.impl;

import com.epam.committee.command.Command;
import com.epam.committee.command.ConstantName;
import com.epam.committee.command.RequestContent;
import com.epam.committee.command.Router;
import com.epam.committee.exception.DaoException;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.FacultyServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteFacultyCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Gets id from the request.
     * Delete faculty from the database and redirects router to the same page.
     *
     * @param content an {@link RequestContent} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     */
    @Override
    public Router execute(RequestContent content) {
        Router router = new Router();
        FacultyServiceImpl facultyService = FacultyServiceImpl.getInstance();
        String start = content.getRequestParameter(ConstantName.PARAMETER_PAGE_START);
        int id = Integer.valueOf(content.getRequestParameter(ConstantName.PARAMETER_FACULTY_ID));
        String page = (String) content.getSessionAttribute(ConstantName.ATTRIBUTE_PAGE_PATH);
        try {
            if (facultyService.deleteFaculty(id)) {
                content.addSessionAttribute(ConstantName.ATTRIBUTE_START, start);
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_REDIRECT_SHOW_FACULTIES));
            } else {
                content.addRequestAttribute(ConstantName.ATTRIBUTE_DELETE_FACULTY_ERROR,
                        MessageManager.getProperty(ConstantName.MESSAGE_DELETING_FACULTY_ERROR));
                router.setPagePath(page);
            }
        } catch (ServiceException | DaoException e) {
            logger.error("Error while deleting faculty", e);
            router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ERROR));
        }
        return router;
    }
}