package com.andela.taccolation.data;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.data.localdata.LocalProfileDataSource;
import com.andela.taccolation.data.remotedata.RemoteProfileDataSource;
import com.andela.taccolation.domain.repository.ProfileRepo;
import com.andela.taccolation.presentation.model.Course;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.model.Teacher;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ProfileRepoImpl implements ProfileRepo {

    private final RemoteProfileDataSource mRemoteProfileDataSource;
    private final LocalProfileDataSource mLocalProfileDataSource;

    @Inject
    public ProfileRepoImpl(RemoteProfileDataSource remoteProfileDataSource, LocalProfileDataSource localProfileDataSource) {
        mRemoteProfileDataSource = remoteProfileDataSource;
        mLocalProfileDataSource = localProfileDataSource;
    }

    @Override
    public void sendCourseList(List<Course> courses) {
        mRemoteProfileDataSource.sendCourseList(courses);
    }

    @Override
    public void sendStudentList(List<Student> students) {
        mRemoteProfileDataSource.sendStudentList(students);
    }

    @Override
    public LiveData<Map<String, List<Student>>> getStudentList(List<String> courseCodeList) {
        return mRemoteProfileDataSource.getStudentList(courseCodeList);
    }

    @Override
    public LiveData<TaskStatus> addStudent(Student student, String studentPhotoPath, byte[] imageData) {
        return mRemoteProfileDataSource.addStudent(student, studentPhotoPath, imageData);
    }

    @Override
    public LiveData<TaskStatus> saveProfileImage(byte[] imageData, Teacher teacher) {
        return mRemoteProfileDataSource.saveProfileImage(imageData, teacher);
    }
}
