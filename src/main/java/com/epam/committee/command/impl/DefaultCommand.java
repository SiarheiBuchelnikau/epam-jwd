package com.epam.committee.command.impl;

import com.epam.committee.command.Command;
import com.epam.committee.command.ConstantName;
import com.epam.committee.command.RequestContent;
import com.epam.committee.command.Router;
import com.epam.committee.util.ConfigurationManager;

public class DefaultCommand implements Command {
    @Override
    public Router execute(RequestContent content) {
        Router router = new Router();
        router.setPagePath(ConfigurationManager.getProperty(ConstantName.JSP_INDEX));
        return router;
    }
}
