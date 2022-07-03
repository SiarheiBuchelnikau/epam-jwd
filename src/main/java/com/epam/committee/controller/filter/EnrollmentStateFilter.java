package com.epam.committee.controller.filter;

import com.epam.committee.command.ConstantName;
import com.epam.committee.entity.Enrollment;
import com.epam.committee.exception.ServiceException;
import com.epam.committee.service.EnrollmentService;
import com.epam.committee.service.impl.EnrollmentServiceImpl;
import com.epam.committee.util.ConfigurationManager;
import com.epam.committee.util.MessageManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class EnrollmentStateFilter implements Filter {

    private final static Logger logger = LogManager.getLogger();
    private EnrollmentService enrollmentService;
    private String contextPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        contextPath = filterConfig.getServletContext().getContextPath();
        EnrollmentServiceImpl enrollmentService = EnrollmentServiceImpl.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        EnrollmentServiceImpl enrollmentService = EnrollmentServiceImpl.getInstance();
        try {
//            enrollmentService = EnrollmentServiceImpl.getInstance();
            Enrollment latestEnrollment = enrollmentService.getLatestEnrollment();
            if (latestEnrollment != null) {
                session.setAttribute(ConstantName.ATTRIBUTE_ENROLLMENT, latestEnrollment);
//                servletRequest.setAttribute(ConstantName.ATTRIBUTE_ENROLLMENT, latestEnrollment);
                logger.info("enrollment has been gotten and set as attribute");
            } else {
                logger.warn("there was no one enrollment");
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ServiceException | NullPointerException e) {
            logger.error(e.getMessage());
//            reInitConnectionPool();
            session.setAttribute(ConstantName.ATTRIBUTE_ERROR, MessageManager.getProperty(ConstantName.MESSAGE_INTERNAL_ERROR));
            request.getRequestDispatcher(ConfigurationManager.getProperty(ConstantName.JSP_ERROR)).forward(request, response);
        }
    }


//    private void reInitConnectionPool() {
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
//        connectionPool.closeAll();
//        connectionPool.getInstance();
//        logger.info("Connection pool has been reinitialized");
//    }

    @Override
    public void destroy() {

    }
}

