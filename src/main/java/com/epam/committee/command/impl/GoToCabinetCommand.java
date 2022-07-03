package com.epam.committee.command.impl;

import com.epam.committee.command.Command;
import com.epam.committee.command.ConstantName;
import com.epam.committee.command.RequestContent;
import com.epam.committee.command.Router;
import com.epam.committee.entity.User;
import com.epam.committee.entity.UserRole;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class GoToCabinetCommand implements Command {
    private final static Logger logger = LogManager.getLogger();

    /**
     * Go to admin or entrant cabinet
     */
    @Override
    public Router execute(RequestContent content) {
        Router router = new Router();
        router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_LOGIN));
        if (content.getSessionAttribute(ConstantName.ATTRIBUTE_USER) != null) {
            User user = (User) content.getSessionAttribute(ConstantName.ATTRIBUTE_USER);
            UserRole userRole = user.getUserRole();
            router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_LOGIN));
            switch (userRole) {
                case ADMIN:
                    router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_ADMIN_CABINET));
                    break;
                case ENTRANT:
                    router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_CLIENT_CABINET));
                    break;
            }
        }
        return router;
    }
}