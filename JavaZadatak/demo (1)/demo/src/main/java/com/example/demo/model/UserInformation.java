package com.example.demo.model;

import java.util.Date;

public class UserInformation {

    public Long id;
    public String firstName;
    public String lastName;
    public Date dateOfBirth;

    public UserInformation() {
    }

    public UserInformation(long id, String firstName, String lastName, Date dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return String.format(
                " firstName=%s, lastName=%s, dateOfBirth=%s ",
                firstName, lastName, dateOfBirth);
    }

}
