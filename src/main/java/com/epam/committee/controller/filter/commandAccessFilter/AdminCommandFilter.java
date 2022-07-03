package com.epam.committee.controller.filter.commandAccessFilter;

import com.epam.committee.entity.UserRole;
import jakarta.servlet.Filter;

public class AdminCommandFilter extends CommandAccessFilter implements Filter {

    {
        exclusiveCommands ="adminCommands";
        userRole= UserRole.ADMIN;
        logMessage="non-admin user tried to perform: ";
    }
}
