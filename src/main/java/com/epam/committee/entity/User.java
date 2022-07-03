package com.epam.committee.entity;

import java.util.Objects;

public class User extends Entity {
    private long userId;
    private String login;
    private String password;
    private UserRole userRole;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private boolean isActive;

    public User() {
    }

    public User(String login, String password, UserRole userRole, String firstName, String lastName, String patronymic, String email, boolean isActive) {
        this.login = login;
        this.password = password;
        this.userRole = userRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.isActive = isActive;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        if (userId != user.userId) {
            return false;
        }
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        int result = 31 * (int) (userId ^ (userId >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User{");
        builder.append("userId").append(userId);
        builder.append(", login=").append(login);
        builder.append(", password=").append(password);
        builder.append(", userRole").append(userRole);
        builder.append("firstName").append(firstName);
        builder.append(", lastName=").append(lastName);
        builder.append(", patronymic=").append(patronymic);
        builder.append(", email").append(email);
        builder.append("isActive").append(isActive);
        builder.append("}");
        return builder.toString();
    }
}