package com.epam.committee.command.impl;

import com.epam.committee.command.Command;
import com.epam.committee.command.ConstantName;
import com.epam.committee.command.RequestContent;
import com.epam.committee.command.Router;
import com.epam.committee.entity.Faculty;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.FacultyServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowAllFacultiesCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Gets all faculties from the database,
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
        FacultyServiceImpl facultyService = FacultyServiceImpl.getInstance();
        List<Faculty> listOfFaculty;
        try {
            listOfFaculty = facultyService.findAllFaculty();
            if (!listOfFaculty.isEmpty()) {
                content.addSessionAttribute(ConstantName.ATTRIBUTE_FACULTY_LIST, listOfFaculty);
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_SHOW_FACULTIES));
            } else {
                content.addRequestAttribute(ConstantName.ATTRIBUTE_SHOW_FACULTY_ERROR,
                        MessageManager.getProperty(ConstantName.MESSAGE_SHOW_FACULTY_ERROR));
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADMIN_CABINET));
            }
        } catch (ServiceException e) {
            logger.error("Error while getting all faculties", e);
            router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ERROR));
        }
        return router;
    }
}