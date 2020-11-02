package com.andela.taccolation.app.utils;

public enum Constants {
    LOG("AppInfo"),
    FILE_PROVIDER("com.andela.taccolation.fileprovider"),
    FIRST_RUN("com.andela.taccolation.FIRST_RUN"),
    USER_AUTHENTICATED("com.andela.taccolation.USER_AUTHENTICATED"),
    TEACHER("teachers"),
    STUDENTS("students"),
    TEACHER_IMAGE_PATH("images/teacher/"),
    STUDENT_IMAGE_PATH("images/student/"),
    COURSES("courses");

    private String constant;

    Constants(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }
}
