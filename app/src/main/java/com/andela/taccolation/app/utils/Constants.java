package com.andela.taccolation.app.utils;

public enum Constants {
    LOG("AppInfo"),
    FIRST_RUN("com.andela.taccolation.FIRST_RUN"),
    USER_AUTHENTICATED("com.andela.taccolation.USER_AUTHENTICATED"),
    TEACHER("teachers"),
    STUDENTS("students"),
    COURSES("courses");

    private String constant;

    Constants(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }
}
