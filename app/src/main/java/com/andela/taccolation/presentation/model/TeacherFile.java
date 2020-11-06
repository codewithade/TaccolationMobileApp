package com.andela.taccolation.presentation.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class TeacherFile {
    private String id;
    private String title;
    private String url;
    private String courseCode;

    public TeacherFile() {
    }

    public TeacherFile(String id, String title, String url, String courseCode) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.courseCode = courseCode;
    }

    public TeacherFile(String title, String courseCode) {
        this.title = title;
        this.courseCode = courseCode;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getCourseCode() {
        return courseCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeacherFile)) return false;
        TeacherFile that = (TeacherFile) o;
        return getId().equals(that.getId()) &&
                getTitle().equals(that.getTitle()) &&
                Objects.equals(getUrl(), that.getUrl()) &&
                Objects.equals(getCourseCode(), that.getCourseCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getUrl(), getCourseCode());
    }

    @NonNull
    @Override
    public String toString() {
        return "TeacherFile{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", courseCode='" + courseCode + '\'' +
                '}';
    }
}
