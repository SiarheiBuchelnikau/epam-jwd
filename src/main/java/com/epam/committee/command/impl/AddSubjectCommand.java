package com.epam.committee.command.impl;

import com.epam.committee.command.*;
import com.epam.committee.entity.Subject;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.SubjectServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import com.epam.committee.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class AddSubjectCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Gets name from the request.
     * Validates this values, if input data is not valid, returns router to the same page
     * with message about invalid input data.
     * Otherwise, creates and adds new subject to the database and redirects router to the same page.
     *
     * @param content an {@link RequestContent} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @see DataValidator#validateSubjectInputData(Map)
     */
    @Override
    public Router execute(RequestContent content) {
        Router router = new Router();
        SubjectServiceImpl subjectService = SubjectServiceImpl.getInstance();
        DataValidator validator = new DataValidator();
        Map<String, String> subjectParameters = new HashMap<>();
        String name = content.getRequestParameter(ConstantName.PARAMETER_SUBJECT_NAME.trim());
        Subject subject = (Subject) content.getSessionAttribute(ConstantName.PARAMETER_SUBJECT_NAME);
        subjectParameters.put(ConstantName.PARAMETER_SUBJECT_NAME, name);
        validator.validateSubjectInputData(subjectParameters);
        try {
            if (!subjectParameters.containsValue(null) &&
                    !subjectParameters.containsValue(ConstantName.ATTRIBUTE_EMPTY_VALUE)) {
                if (subjectService.addSubject(subjectParameters)) {
                    router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADD_SUBJECT));
                    router.setType(RouteType.REDIRECT);
                } else {
                    content.addRequestAttribute(ConstantName.ATTRIBUTE_ADD_SUBJECT_ERROR,
                            MessageManager.getProperty(ConstantName.MESSAGE_ADD_SUBJECT_ERROR));
                    router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADD_SUBJECT));
                }
            } else {
                content.addSessionAttribute(ConstantName.ATTRIBUTE_VALIDATED_MAP, subjectParameters);
                content.addRequestAttribute(ConstantName.ATTRIBUTE_VALIDATE_SUBJECT_ERROR,
                        MessageManager.getProperty(ConstantName.MESSAGE_VALIDATE_SUBJECT_ERROR));
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADD_SUBJECT));
            }
        } catch (ServiceException e) {
            logger.error("Error adding subject", e);
            router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ERROR));
        }
        return router;
    }
}