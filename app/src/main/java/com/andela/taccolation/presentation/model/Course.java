package com.andela.taccolation.presentation.model;

import java.util.List;

public class Course {
    private String title;
    private String code;
    private int courseUnit;
    private List<String> teacherIdList; // unique Teacher Id makes it easy to query the db to retrieve the Teacher details given the ID

    public Course(String title, String code, int courseUnit, List<String> teacherIdList) {
        this.title = title;
        this.code = code;
        this.courseUnit = courseUnit;
        this.teacherIdList = teacherIdList;
    }

    public Course() {
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public int getCourseUnit() {
        return courseUnit;
    }

    public List<String> getTeachers() {
        return teacherIdList;
    }
}
