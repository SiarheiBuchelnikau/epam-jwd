package com.epam.committee.command.impl;

import com.epam.committee.command.Command;
import com.epam.committee.command.ConstantName;
import com.epam.committee.command.RequestContent;
import com.epam.committee.command.Router;
import com.epam.committee.util.ConfigurationManager;

public class LogoutCommand implements Command {
    /**
     * Invalidates user session.
     * Returns router to the main page.
     *
     * @param content an {@link RequestContent} object that
     *                contains the request the client has made
     *                of the servlet
     * @return a {@code Router} object
     */
    @Override
    public Router execute(RequestContent content) {
        Router router = new Router();
        content.setInvalidateSession(true);
        router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_INDEX));
        return router;
    }
}

