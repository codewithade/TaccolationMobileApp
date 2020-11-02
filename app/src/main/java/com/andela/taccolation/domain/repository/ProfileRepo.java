package com.andela.taccolation.domain.repository;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.presentation.model.Course;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.model.Teacher;

import java.util.List;
import java.util.Map;

public interface ProfileRepo {
    void sendCourseList(List<Course> courses);

    void sendStudentList(List<Student> students);

    LiveData<Map<String, List<Student>>> getStudentList(List<String> courseCodeList);

    LiveData<TaskStatus> addStudent(Student student, String studentPhotoPath, byte[] imageData);

    LiveData<TaskStatus> saveProfileImage(byte[] imageData, Teacher teacher);
}
