package com.andela.taccolation.presentation.model;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import java.util.List;
import java.util.Objects;

public class Teacher {

    private String firstName;
    private String lastName;
    private String designation;
    private List<String> courseCodeList; // unique course codes can be used to query the db to retrieve the Course details
    private String id;
    private String email;
    private String imageUrl;
    @Exclude
    private String password;

    public Teacher() {
    }

    public Teacher(String firstName, String lastName, String designation, List<String> courseCodeList, String id, String email, String imageUrl, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.designation = designation;
        this.courseCodeList = courseCodeList;
        this.id = id;
        this.email = email;
        this.imageUrl = imageUrl;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return getFirstName().equals(teacher.getFirstName()) &&
                getLastName().equals(teacher.getLastName()) &&
                getDesignation().equals(teacher.getDesignation()) &&
                Objects.equals(getCourseCodeList(), teacher.getCourseCodeList()) &&
                Objects.equals(getId(), teacher.getId()) &&
                getEmail().equals(teacher.getEmail()) &&
                Objects.equals(getImageUrl(), teacher.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getDesignation(), getCourseCodeList(), getId(), getEmail(), getPassword());
    }

    // FIXME: 19/10/2020 delete this security risk


    @NonNull
    @Override
    public String toString() {
        return "Teacher{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", designation='" + designation + '\'' +
                ", courseCodeList=" + courseCodeList +
                ", id='" + id + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public List<String> getCourseCodeList() {
        return courseCodeList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Exclude
    public String getPassword() {
        return password;
    }
}
