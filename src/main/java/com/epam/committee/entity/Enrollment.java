package com.epam.committee.entity;

import java.sql.Timestamp;

public class Enrollment extends Entity {

    private int enrollmentId;
    private EnrollmentState state;
    private Timestamp startDate;
    private Timestamp endDate;

    public Enrollment() {
    }

    public Enrollment(int enrollmentId, EnrollmentState state, Timestamp startDate, Timestamp endDate) {
        this.enrollmentId = enrollmentId;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public EnrollmentState getState() {
        return state;
    }

    public void setState(EnrollmentState state) {
        this.state = state;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enrollment that = (Enrollment) o;
        return enrollmentId == that.enrollmentId;
    }

    @Override
    public int hashCode() {
        int result = 31 * (int) (enrollmentId ^ (enrollmentId >>> 32));
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Enrollment{");
        builder.append("enrollmentId=").append(enrollmentId);
        builder.append(", state=").append(state);
        builder.append(", startDate=").append(startDate);
        builder.append(", endDate").append(endDate);
        builder.append("}");
        return builder.toString();
    }
}
