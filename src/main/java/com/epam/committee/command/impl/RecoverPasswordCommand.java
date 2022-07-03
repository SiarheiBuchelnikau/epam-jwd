package com.epam.committee.command.impl;

import com.epam.committee.command.Command;
import com.epam.committee.command.ConstantName;
import com.epam.committee.command.RequestContent;
import com.epam.committee.command.Router;
import com.epam.committee.email.SendEmail;
import com.epam.committee.entity.User;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.UserServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import com.epam.committee.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class RecoverPasswordCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(RequestContent content) {
        Router router = new Router();
        SendEmail sendEmail = new SendEmail();
        DataValidator validator = new DataValidator();
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String login = content.getRequestParameter(ConstantName.PARAMETER_LOGIN).trim();
        try {
            validator.validateLogin(login);
            if (userService.checkUserLogin(login)) {
                Optional<User> userOptional = userService.findByLogin(login);
                if (userOptional.isPresent()) {
                    if (userService.changePassword(userOptional.get(), ConstantName.EMAIL_TEMPORARY_PASSWORD)) {
                        // should be sendEmail sendTo -   userOptional.get().getEmail() instead of ConstantName.REAL_EMAIL_FOR_TEST
                        sendEmail.send(ConstantName.REAL_EMAIL_FOR_TEST, ConstantName.SUBJECT_PASSWORD_RECOVER, ConstantName.EMAIL_PASSWORD_RECOVER);
                        content.addRequestAttribute(ConstantName.ATTRIBUTE_PASSWORD_RECOVER_SUCCESS,
                                MessageManager.getProperty(ConstantName.MESSAGE_PASSWORD_RECOVER_SUCCESS));
                        router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_LOGIN));
                    } else {
                        setErrorMessage(content, router);
                    }
                } else {
                    setErrorMessage(content, router);
                }
            } else {
                content.addRequestAttribute(ConstantName.ATTRIBUTE_PASSWORD_RECOVER_NO_LOGIN,
                        MessageManager.getProperty(ConstantName.MESSAGE_PASSWORD_RECOVER_NO_LOGIN));
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_PASSWORD_RECOVER));
            }
        } catch (ServiceException e) {
            logger.error("Error while recovering password", e);
            router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ERROR));
        }
        return router;
    }

    private void setErrorMessage(RequestContent content, Router router) {
        content.addRequestAttribute(ConstantName.ATTRIBUTE_PASSWORD_RECOVER_ERROR,
                MessageManager.getProperty(ConstantName.MESSAGE_PASSWORD_RECOVER_ERROR));
        router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_PASSWORD_RECOVER));
    }
}