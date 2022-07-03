package com.epam.committee.command.impl;

import com.epam.committee.command.*;
import com.epam.committee.entity.*;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.ApplicantServiceImpl;
import com.epam.committee.service.impl.FacultyServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import com.epam.committee.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddEntrantCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Gets user, faculty, enrollment the requestand session.
     * <p>
     * Otherwise, creates and adds new entrant to the database and redirects router to entrant cabinet page.
     *
     * @param content an {@link RequestContent} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     */
    @Override

    public Router execute(RequestContent content) {
        Router router = new Router();
//        SubjectServiceImpl subjectService = SubjectServiceImpl.getInstance();
        FacultyServiceImpl facultyService = FacultyServiceImpl.getInstance();
        ApplicantServiceImpl applicantService = ApplicantServiceImpl.getInstance();
//        List<Subject> subjectList;
        DataValidator validator = new DataValidator();
//        Map<String, String> facultyParameters = new HashMap<>();
        User user = (User) content.getSessionAttribute(ConstantName.ATTRIBUTE_USER);
        logger.info("AddEntrantCommand - user: " + user);
        String facultyIdString = content.getRequestParameter(ConstantName.PARAMETER_FACULTY_ID).trim();
        logger.info("AddEntrantCommand - facultyId: " + facultyIdString);
        Enrollment enrollment = (Enrollment) content.getSessionAttribute(ConstantName.ATTRIBUTE_ENROLLMENT);
        logger.info("AddEntrantCommand - enrollment: " + enrollment);

        if (enrollment != null && enrollment.getState() == EnrollmentState.OPENED) {
            if (facultyIdString != null && user != null && user.getUserRole() == UserRole.ENTRANT) {
                int facultyId = Integer.parseInt(facultyIdString);

                try {
                    if (content.getRequestMethod().equals("POST")) {
                        String[] subjectGradesStrings = content.getRequestParam(ConstantName.PARAMETER_GRADE);
                        if (validator.validateGrades(subjectGradesStrings)) {
                            Applicant newApplicant = new Applicant();
                            newApplicant.setUserId((int) (user.getUserId()));
                            newApplicant.setId((int) (user.getUserId()));
                            newApplicant.setFacultyId(facultyId);
                            newApplicant.setEnrollmentId(enrollment.getEnrollmentId());
                            newApplicant.setApplicantState(ApplicantState.APPLIED);

                            if (applicantService.addApplicant(newApplicant, subjectGradesStrings)) {
                                logger.info("applicant has been added: " + newApplicant);

                                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ENTRANT_CABINET));
                                router.setType(RouteType.REDIRECT);
                            } else {
                                logger.warn("trying to apply while already applied: " + user);
                                content.addSessionAttribute(ConstantName.ATTRIBUTE_ADD_ENTRANT_ERROR,
                                        MessageManager.getProperty(ConstantName.MESSAGE_ALREADY_APPLIED));
                                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADD_ENTRANT));
                            }
                        } else {
                            logger.warn("trying to insert invalid grade");
                            content.addSessionAttribute(ConstantName.ATTRIBUTE_ADD_ENTRANT_ERROR,
                                    MessageManager.getProperty(ConstantName.MESSAGE_INVALID_GRADE));
                            router.getPagePath();
                            router.setType(RouteType.REDIRECT);
                        }
                    } else {
                        Faculty faculty = facultyService.getById(facultyId);
                        content.addRequestAttribute(ConstantName.ATTRIBUTE_FACULTY, faculty);
                        logger.info("faculty set as attribute");
                        router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADD_ENTRANT));
                    }

                } catch (ServiceException e) {
                    logger.error(e.getMessage());
                    content.addSessionAttribute(ConstantName.ATTRIBUTE_ERROR, MessageManager.getProperty(ConstantName.MESSAGE_INTERNAL_ERROR));
                }
            } else {
                logger.warn("wrong input parameters");
                content.addSessionAttribute(ConstantName.ATTRIBUTE_ERROR, MessageManager.getProperty(ConstantName.MESSAGE_NO_ACCESS));
            }
        } else {
            logger.warn(MessageManager.getProperty(ConstantName.MESSAGE_WRONG_ENROLLMENT_STATE));
            content.addSessionAttribute(ConstantName.ATTRIBUTE_ERROR, MessageManager.getProperty(ConstantName.MESSAGE_WRONG_ENROLLMENT_STATE));
        }

        return router;
    }
}