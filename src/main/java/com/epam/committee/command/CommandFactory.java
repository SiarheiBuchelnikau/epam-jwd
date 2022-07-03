package com.epam.committee.command;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * Define command.
 *
 * @return the Optional<Command>
 */
public class CommandFactory {
    private final static Logger logger = LogManager.getLogger();

    public Optional<Command> defineCommand(RequestContent content) {
        Optional<Command> current;
        String action = content.getRequestParameter("command");
        if (action == null || action.isEmpty()) {
            return Optional.empty();
        }
        try {
            CommandType currentType = CommandType.valueOf(action.toUpperCase());
            current = Optional.of(currentType.getCurrentCommand());
        } catch (IllegalArgumentException e) {
            current = Optional.empty();
            content.addRequestAttribute("wrongAction", "message.wrongAction");
            logger.log(Level.ERROR, "Can't define command", e);
        }
        return current;
    }
}
