package com.epam.committee.entity;

import java.util.List;

public class Applicant extends User implements Comparable<Applicant> {

    private int id;
    private int facultyId;
    private String facultyName;
    private int enrollmentId;
    private ApplicantState applicantState;
    private int totalRating;
    private List<Subject> subjects;

    public Applicant() {

    }

    public Applicant(String login, String password, UserRole userRole, String firstName, String lastName, String patronymic, String email, boolean isActive, int id, int facultyId, String facultyName, int enrollmentId, ApplicantState applicantState, int totalRating, List<Subject> subjects) {
        super(login, password, userRole, firstName, lastName, patronymic, email, isActive);
        this.id = id;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.enrollmentId = enrollmentId;
        this.applicantState = applicantState;
        this.totalRating = totalRating;
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public ApplicantState getApplicantState() {
        return applicantState;
    }

    public void setApplicantState(ApplicantState applicantState) {
        this.applicantState = applicantState;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }


    public int compareTo(Applicant applicant) {
        return applicant.totalRating != totalRating ? Integer.compare(applicant.totalRating, totalRating) : Integer.compare(id, applicant.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Applicant applicant = (Applicant) o;
        return id == applicant.id;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (facultyId ^ (facultyId >>> 32));
        result = 31 * result + (facultyName != null ? facultyName.hashCode() : 0);
        result = 31 * result + (int) (enrollmentId ^ (enrollmentId >>> 32));
        result = 31 * result + (applicantState != null ? applicantState.hashCode() : 0);
        result = 31 * result + (int) (totalRating ^ (totalRating >>> 32));
        result = 31 * result + (subjects != null ? subjects.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Applicant{");
        builder.append("id=").append(id);
        builder.append(", facultyName=").append(facultyName);
        builder.append(", enrollmentId=").append(enrollmentId);
        builder.append(", applicantState").append(applicantState);
        builder.append("applicantState").append(totalRating);
        builder.append("}");
        return builder.toString();
    }
}