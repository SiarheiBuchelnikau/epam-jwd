package com.epam.committee.entity;

import java.util.List;
import java.util.Objects;

public class Faculty extends Entity {

    private int id;
    private String name;
    private int capacity;
    private List<Subject> requiredSubjects;

    public Faculty() {

    }

    public Faculty(int id, String name, int capacity, List<Subject> requiredSubjects) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.requiredSubjects = requiredSubjects;
    }

    public Faculty(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    public int getFacultyId() {
        return id;
    }

    public void setFacultyId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Subject> getRequiredSubjects() {
        return requiredSubjects;
    }

    public void setRequiredSubjects(List<Subject> requiredSubjects) {
        this.requiredSubjects = requiredSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Faculty faculty = (Faculty) o;
        if (id != faculty.id) {
            return false;
        }
        if (capacity != faculty.capacity) {
            return false;
        }
        return Objects.equals(name, faculty.name);
    }

    @Override
    public int hashCode() {
        int result = (31 * (id ^ (id >>> 32)));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + capacity;
        result = 31 * result + (requiredSubjects != null ? requiredSubjects.hashCode() : 0);
        return result;
    }

    @Override

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Faculty{");
        builder.append("name=").append(name);
        builder.append(", capacity=");
        builder.append("name=").append(capacity);
        builder.append(", requiredSubjects=");
        builder.append("name=").append(requiredSubjects);
        builder.append("}");
        return builder.toString();
    }
}
