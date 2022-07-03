package com.epam.committee.command;

@FunctionalInterface
public interface Command {
    /**
     * Execute.
     *
     * @param content the request
     * @return the Router
     */
    Router execute(RequestContent content);
    default void refresh(){}
}





