package com.epam.committee.command;

import com.epam.committee.command.impl.*;

public enum CommandType {

    ERROR {{
        this.command = new ErrorCommand();
    }},
    CHANGE_LANGUAGE {{
        this.command = new LanguageCommand();
    }},
    REGISTRATION {{
        this.command = new RegistrationCommand();
    }},
    LOGIN {{
        this.command = new LoginCommand();
    }},
    LOGOUT {{
        this.command = new LogoutCommand();
    }},
    RECOVER_PASSWORD {{
        this.command = new RecoverPasswordCommand();
    }},
    ADD_SUBJECT {{
        this.command = new AddSubjectCommand();
    }},
    DELETE_SUBJECT {{
        this.command = new DeleteSubjectCommand();
    }},
    ADD_FACULTY {{
        this.command = new AddFacultyCommand();
    }},
    DELETE_FACULTY {{
        this.command = new DeleteFacultyCommand();
    }},
    GO_TO_CABINET {{
        this.command = new GoToCabinetCommand();
    }},
    SHOW_ALL_SUBJECTS {{
        this.command = new ShowAllSubjectsCommand();
    }},
    SHOW_ALL_FACULTIES {{
        this.command = new ShowAllFacultiesCommand();
    }},
    SHOW_FACULTY {{
        this.command = new ShowFacultyCommand();
    }},
    OPEN_ENROLLMENT {{
        this.command = new OpenEnrollmentCommand();
    }},
    CLOSE_ENROLLMENT {{
        this.command = new CloseEnrollmentCommand();
    }},
    SHOW_ENROLLMENTS {{
        this.command = new ShowEnrollmentsCommand();
    }},
    ADD_ENTRANT {{
        this.command = new AddEntrantCommand();
    }},
    SHOW_APPLICANTS {{
        this.command = new ShowApplicantsCommand();
    }},
    SHOW_APPLICATION {{
        this.command = new ShowApplicationCommand();
    }};

    Command command;

    public Command getCurrentCommand() {
        return command;
    }

}
