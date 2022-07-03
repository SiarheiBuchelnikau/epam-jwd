package com.epam.committee.entity;

import java.util.Objects;

public class Subject extends Entity {

    private int subject_id;
    private String name;
    private int grade;

    public Subject() {
    }

    public Subject(int subject_id, String name, int grade) {
        this.subject_id = subject_id;
        this.name = name;
        this.grade = grade;
    }

    public Subject(String name) {
        this.name = name;
    }

    public int getSubjectId() {
        return subject_id;
    }

    public void setSubjectId(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subject subject = (Subject) o;
        if (subject_id != subject.subject_id) {
            return false;
        }
        return Objects.equals(name, subject.name);
    }

    @Override
    public int hashCode() {
        int result = (31 * subject_id + (subject_id ^ (subject_id >>> 32)));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Subject{");
        builder.append("name=").append(name);
        builder.append("}");
        return builder.toString();
    }
}
