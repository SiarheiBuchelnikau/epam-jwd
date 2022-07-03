package com.epam.committee.command.impl;

import com.epam.committee.command.*;
import com.epam.committee.entity.User;
import com.epam.committee.entity.UserRole;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.impl.UserServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LoginCommand implements Command {

    private final static Logger logger = LogManager.getLogger();

    /**
     * Gets login and password values from the request.
     * Validates this values, if input data is not valid, or no such user is presented in the database (user is null),
     * or user is blocked, returns router to the same page with message about incorrect login or password.
     * Otherwise, finds the user by this values and sets sessions attributes and
     * returns router to the user's cabinet page.
     *
     * @param content an {@link RequestContent} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     * @see UserServiceImpl#findUserByLoginAndPassword(String, String)
     * @see UserServiceImpl#getUserRoleId(String)
     * @see UserServiceImpl#findByLogin(String)
     */
    @Override
    public Router execute(RequestContent content) {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        User user;
        Router router = new Router();
        String login = content.getRequestParameter(ConstantName.PARAMETER_LOGIN);
        String password = content.getRequestParameter(ConstantName.PARAMETER_PASSWORD);
        try {
            if (userService.findUserByLoginAndPassword(login, password)) {
                Optional<User> userOptional = userService.findByLogin(login);
                if (userOptional.isPresent()) {
                    user = userOptional.get();
                    if (user.getIsActive()) {
                        content.addSessionAttribute(ConstantName.ATTRIBUTE_USER, user);
                        UserRole userRole = user.getUserRole();
                        switch (userRole) {
                            case ADMIN:
                                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADMIN_CABINET));
                                break;
                            case ENTRANT:
                                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_CLIENT_CABINET));
                                break;
                            default:
                                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_LOGIN));
                        }
                    } else {
                        content.addRequestAttribute(ConstantName.ATTRIBUTE_USER_IS_BLOCKED_ERROR,
                                MessageManager.getProperty(ConstantName.MESSAGE_BLOCKED_USER_ERROR));
                        router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_INDEX));
                        router.setType(RouteType.FORWARD);
                    }
                } else {
                    content.addRequestAttribute(ConstantName.ATTRIBUTE_LOGIN_ERROR,
                            MessageManager.getProperty(ConstantName.MESSAGE_LOGIN_ERROR));
                    router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_LOGIN));
                }
            } else {
                content.addRequestAttribute(ConstantName.ATTRIBUTE_LOGIN_ERROR,
                        MessageManager.getProperty(ConstantName.MESSAGE_LOGIN_ERROR));
                router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_LOGIN));
            }
        } catch (ServiceException e) {
            logger.error("Error while login command", e);
            router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ERROR));
            router.setType(RouteType.REDIRECT);
        }
        return router;
    }
}
