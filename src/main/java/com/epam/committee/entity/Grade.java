package com.epam.committee.entity;

public class Grade extends Entity {
    private int subject_grades_id;
    private int grade;
    private int subject_id;
    private int applicant_id;

    public Grade() {

    }

    public Grade(int grade, int subject_id, int applicant_id) {
        this.grade = grade;
        this.subject_id = subject_id;
        this.applicant_id = applicant_id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getSubjectId() {
        return subject_id;
    }

    public void setSubjectId(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getApplicantId() {
        return applicant_id;
    }

    public void setApplicantId(int applicant_id) {
        this.applicant_id = applicant_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Grade grade = (Grade) o;
        if (subject_id != grade.subject_id) {
            return false;
        }
        return applicant_id == grade.applicant_id;
    }

    @Override
    public int hashCode() {
        int result = 31 * (int) (grade ^ (grade >>> 32));
        result = 31 * result + (int) (subject_id ^ (subject_id >>> 32));
        result = 31 * result + (int) (applicant_id ^ (applicant_id >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Grade{");
        builder.append("grade=").append(grade);
        builder.append("}");
        return builder.toString();
    }
}
