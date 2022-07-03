package com.epam.committee.command.impl;

import com.epam.committee.command.*;
import com.epam.committee.entity.Faculty;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.FacultyServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShowFacultyCommand implements Command {

    private final static Logger logger = LogManager.getLogger();

    /**
     * Gets faculty from the database,
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
        FacultyServiceImpl facultyService = FacultyServiceImpl.getInstance();
        int id = Integer.parseInt(content.getRequestParameter(ConstantName.PARAMETER_FACULTY_ID));
        try {
            Faculty faculty = facultyService.getById(id);
            if (faculty != null) {
                logger.info("faculty and required subjects &&&&&&&& were found and set as attribute");

                content.addSessionAttribute(ConstantName.ATTRIBUTE_FACULTY, faculty);
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_SHOW_FACULTY));
            } else {
                logger.warn("faculty wasn't found");
            }
        } catch (ServiceException e) {
            logger.error("Error while show faculty command", e);
            router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ERROR));
            router.setType(RouteType.REDIRECT);
        }
        return router;
    }
}