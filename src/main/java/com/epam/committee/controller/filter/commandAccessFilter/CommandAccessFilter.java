package com.epam.committee.controller.filter.commandAccessFilter;

import com.epam.committee.command.ConstantName;
import com.epam.committee.entity.User;
import com.epam.committee.entity.UserRole;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class CommandAccessFilter implements Filter {
    private final static Logger logger = LogManager.getLogger();
    private static final String DELIMITER = " ";

    private String contextPath;
    private List<String> userCommands;
    String exclusiveCommands;
    UserRole userRole;
    String logMessage;



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        contextPath = filterConfig.getServletContext().getContextPath();
        String[] commands = filterConfig.getInitParameter(exclusiveCommands).split(DELIMITER);
        userCommands = Arrays.asList(commands);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String command = request.getParameter(ConstantName.PARAM_NAME_COMMAND);
        User user = (User) request.getSession().getAttribute(ConstantName.ATTRIBUTE_USER);

        if ((user == null ||  user.getUserRole() != userRole) && userCommands.contains(command)) {
            logger.debug(logMessage + command);
            request.getSession().setAttribute(ConstantName.ATTRIBUTE_ERROR, MessageManager.getProperty(ConstantName.MESSAGE_NO_ACCESS));
            response.sendRedirect(contextPath + "/" + ConfigurationManager.getProperty(ConstantName.JSP_REDIRECT_ERROR));
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

