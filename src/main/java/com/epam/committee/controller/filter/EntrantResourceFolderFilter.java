package com.epam.committee.controller.filter;

import com.epam.committee.command.ConstantName;
import com.epam.committee.entity.User;
import com.epam.committee.entity.UserRole;
import com.epam.committee.util.ConfigurationManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EntrantResourceFolderFilter implements Filter {

    private final static Logger logger = LogManager.getLogger();

    private String contextPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        contextPath = filterConfig.getServletContext().getContextPath();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession().getAttribute(ConstantName.ATTRIBUTE_USER);

        if (user != null && user.getUserRole() == UserRole.ENTRANT) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            logger.warn("non-entrant user tried to enter resource folder");
            response.sendRedirect(contextPath + "/" + ConfigurationManager.getProperty(ConstantName.JSP_REDIRECT_ERROR));
        }
    }

    @Override
    public void destroy() {

    }
}
