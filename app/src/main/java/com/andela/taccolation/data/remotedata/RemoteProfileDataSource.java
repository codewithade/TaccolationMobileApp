package com.andela.taccolation.data.remotedata;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.presentation.model.Course;
import com.andela.taccolation.presentation.model.Student;

import java.util.List;
import java.util.Map;

public interface RemoteProfileDataSource {
    void sendCourseList(List<Course> courses);

    void sendStudentList(List<Student> students);

    LiveData<TaskStatus> addStudent(Student student);

    LiveData<Map<String, List<Student>>> getStudentList(List<String> courseCodeList);
}
