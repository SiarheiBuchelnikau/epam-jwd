package com.epam.committee.command.impl;

import com.epam.committee.command.*;
import com.epam.committee.entity.Faculty;
import com.epam.committee.entity.Subject;
import com.epam.committee.exception.DaoException;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.FacultyServiceImpl;
import com.epam.committee.service.impl.SubjectServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import com.epam.committee.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFacultyCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Gets name, capacity, requed subjects from the request.
     * Validates this values, if input data is not valid, returns router to the same page
     * with message about invalid input data.
     * Otherwise, creates and adds new faculty to the database and redirects router to the same page.
     *
     * @param content an {@link RequestContent} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * //     * @see DataValidator#isValidString(String)
     * //     * @see FacultyServiceImpl#addFaculty(Map)
     */
    @Override
    public Router execute(RequestContent content) {

        Router router = new Router();
        SubjectServiceImpl subjectService = SubjectServiceImpl.getInstance();
        FacultyServiceImpl facultyService = FacultyServiceImpl.getInstance();
        List<Subject> subjectList;
        DataValidator validator = new DataValidator();
        Map<String, String> facultyParameters = new HashMap<>();
        try {
            if (content.getRequestMethod().equals("GET")) {
                subjectList = subjectService.findAllSubject();
                content.addSessionAttribute(ConstantName.ATTRIBUTE_SUBJECT_LIST, subjectList);
                logger.info("subjects has been gotten and set as attribute");
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADD_FACULTY));
                router.setType(RouteType.FORWARD);
            } else {
                String facultyNameUnparsed = content.getRequestParameter(ConstantName.PARAMETER_FACULTY_NAME).trim();
                String capacityUnparsed = content.getRequestParameter(ConstantName.PARAMETER_FACULTY_CAPACITY).trim();
                String[] subjectsId = content.getRequestParam(ConstantName.ATTRIBUTE_SUBJECT_ID_LIST);

                if (validator.validateFacultyName(facultyNameUnparsed) && subjectsId != null && DataValidator.validateCapacity(capacityUnparsed)) {
                    //             if (1 == 1) {
                    List<Subject> requiredSubjects = new ArrayList<>();
                    Subject subject;
                    for (String idString : subjectsId) {
                        subject = new Subject();
                        int c = (Integer.parseInt(idString));
                        subject.setSubjectId(c);
                        requiredSubjects.add(subject);
                        logger.info("subject has been added: " + c);
                    }
                    Faculty faculty = new Faculty();
                    faculty.setName(facultyNameUnparsed);
                    logger.info("faculty name has been added: " + facultyNameUnparsed);
                    faculty.setCapacity(Integer.parseInt(capacityUnparsed));
                    logger.info("faculty capacity has been added: " + capacityUnparsed);
                    faculty.setRequiredSubjects(requiredSubjects);
                    logger.info("faculty RequiredSubjects has been added: ");
                    facultyService.addFaculty(faculty);
                    logger.info("faculty has been added: " + faculty);
                    router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_REDIRECT_SHOW_FACULTIES));
                    router.setType(RouteType.REDIRECT);
                } else {
                    router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADD_FACULTY));
                    router.setType(RouteType.REDIRECT);
                    content.addRequestAttribute(ConstantName.ATTRIBUTE_ADD_FACULTY_ERROR,
                            MessageManager.getProperty(ConstantName.MESSAGE_ADD_FACULTY_ERROR));
                    logger.warn("Error adding faculty");
                }
            }
        } catch (ServiceException | NumberFormatException | DaoException e) {
            logger.error(e.getMessage());
            content.addRequestAttribute(ConstantName.ATTRIBUTE_ADD_FACULTY_ERROR,
                    MessageManager.getProperty(ConstantName.MESSAGE_ADD_FACULTY_ERROR));
        }
        return router;
    }
}