package com.andela.taccolation.data.remotedata;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.presentation.model.Course;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.model.Teacher;

import java.util.List;
import java.util.Map;

public interface RemoteProfileDataSource {
    void sendCourseList(List<Course> courses);

    void sendStudentList(List<Student> students);

    LiveData<TaskStatus> addStudent(Student student, String studentPhotoPath, byte[] imageData);

    LiveData<Map<String, List<Student>>> getStudentList(List<String> courseCodeList);

    LiveData<TaskStatus> saveProfileImage(byte[] imageData, Teacher teacher);

    LiveData<List<Course>> getCourseList();
}
