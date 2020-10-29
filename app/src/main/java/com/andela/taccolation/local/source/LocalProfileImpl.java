package com.andela.taccolation.local.source;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.data.localdata.LocalProfileDataSource;
import com.andela.taccolation.presentation.model.Course;
import com.andela.taccolation.presentation.model.Student;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class LocalProfileImpl implements LocalProfileDataSource {

    @Inject
    public LocalProfileImpl() {
    }

    @Override
    public void sendCourseList(List<Course> courses) {

    }

    @Override
    public void sendStudentList(List<Student> students) {

    }

    @Override
    public LiveData<Map<String, List<Student>>> getStudentList(List<String> courseCodeList) {
        return null;
    }

    @Override
    public LiveData<TaskStatus> addStudent(Student student) {
        return null;
    }
}