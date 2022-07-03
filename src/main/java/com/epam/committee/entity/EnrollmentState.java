package com.epam.committee.entity;

public enum EnrollmentState {
    OPENED(1),
    CLOSED(2);

    private int id;

    EnrollmentState(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
