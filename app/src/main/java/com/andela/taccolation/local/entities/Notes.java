package com.andela.taccolation.local.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Notes {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "course_code")
    private String courseCode;

    @ColumnInfo(name = "file_path")
    private String filePath;

    @Override
    public String toString() {
        return "Notes{" +
                "uid=" + uid +
                ", title='" + title + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notes)) return false;
        Notes notes = (Notes) o;
        return uid == notes.uid &&
                getTitle().equals(notes.getTitle()) &&
                getCourseCode().equals(notes.getCourseCode()) &&
                getFilePath().equals(notes.getFilePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, getTitle(), getCourseCode(), getFilePath());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
