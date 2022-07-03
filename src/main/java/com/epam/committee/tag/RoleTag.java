package com.epam.committee.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

public class RoleTag extends TagSupport {
    private final static String ADMIN = "ADMIN";
    private final static String ENTRANT = "ENTRANT";
    private final static String GUEST = "GUEST";
    private final static String ROLE = "Role: ";
    private final static String TAGS = "<h5> ";
    private final static String TAGS_END = " </h5>";
    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            StringBuilder builder = new StringBuilder();
            String to;
            if (ADMIN.equalsIgnoreCase(role)) {
                to = builder.append(ROLE).append(role).toString();
            } else if (ENTRANT.equalsIgnoreCase(role)) {
                to = builder.append(ROLE).append(role).toString();
            } else {
                to = builder.append(ROLE).append(GUEST).toString();
            }
            // String output = builder.append(TAGS).append(to).append(TAGS_END).toString();
            pageContext.getOut().write(to);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}