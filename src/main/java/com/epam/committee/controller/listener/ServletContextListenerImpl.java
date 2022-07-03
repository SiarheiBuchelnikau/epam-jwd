package com.epam.committee.controller.listener;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    static Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        //ConnectionPool.getInstance();
        logger.log(Level.INFO, "++++++++++ contextInitialized :" + sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
        logger.log(Level.INFO, "---------- contextDestroyed :" + sce.getServletContext().getContextPath());
        //ConnectionPool.getInstance().destroyPool();
    }
}
