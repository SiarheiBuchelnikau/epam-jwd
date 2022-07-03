package com.epam.committee.controller.filter.commandAccessFilter;

import com.epam.committee.entity.UserRole;
import jakarta.servlet.Filter;

public class EntrantCommandFilter extends CommandAccessFilter implements Filter {

    {
        exclusiveCommands ="entrantCommands";
        userRole= UserRole.ENTRANT;
        logMessage="non-entrant user tried to perform: ";
    }
}
