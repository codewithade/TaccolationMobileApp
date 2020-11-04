package com.andela.taccolation.domain.usecases;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.domain.repository.ProfileRepo;
import com.andela.taccolation.presentation.model.Course;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.model.Teacher;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class ProfileTask {

    private final ProfileRepo mProfileRepo;

    @Inject
    public ProfileTask(ProfileRepo profileRepo) {
        mProfileRepo = profileRepo;
    }

    public void sendCourseList(List<Course> courses) {
        mProfileRepo.sendCourseList(courses);
    }

    public void sendStudentList(List<Student> students) {
        mProfileRepo.sendStudentList(students);
    }

    public LiveData<Map<String, List<Student>>> getStudentList(List<String> courseCodeList) {
        return mProfileRepo.getStudentList(courseCodeList);
    }

    public LiveData<TaskStatus> addStudent(Student student, String studentPhotoPath, byte[] imageData) {
        return mProfileRepo.addStudent(student, studentPhotoPath, imageData);
    }

    public LiveData<TaskStatus> saveProfileImage(byte[] imageData, Teacher teacher) {
        return mProfileRepo.saveProfileImage(imageData, teacher);
    }

    public LiveData<List<Course>> getCourseList() {
        return mProfileRepo.getCourseList();
    }
}
