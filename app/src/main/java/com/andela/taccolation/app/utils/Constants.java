package com.andela.taccolation.app.utils;

public enum Constants {
    LOG("AppInfo"),
    DATABASE_NAME("taccolation.db"),
    FILE_PROVIDER("com.andela.taccolation.fileprovider"),
    FIRST_RUN("com.andela.taccolation.FIRST_RUN"),
    USER_AUTHENTICATED("com.andela.taccolation.USER_AUTHENTICATED"),
    TEACHER("teachers"),
    STUDENTS("students"),
    TEACHER_IMAGE_PATH("images/teacher/"),
    STUDENT_IMAGE_PATH("images/student/"),
    TEACHER_NOTES_PATH("files/teacher/"),
    COURSES("courses");

    private final String constant;

    Constants(String constant) {
        this.constant = constant;
    }

    public String getConstant() {
        return constant;
    }
}
