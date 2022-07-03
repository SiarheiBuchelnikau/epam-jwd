package com.epam.committee.entity;

public enum UserRole {
    ADMIN(1),
    ENTRANT(2);

    private final int id;

    UserRole(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UserRole resolveById(int id) {
        switch (id) {
            case 1:
                return ADMIN;
            case 2:
                return ENTRANT;
            default:
                return null;
        }
    }
}
