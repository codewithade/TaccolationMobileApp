package com.andela.taccolation.presentation.viewmodel;

import android.graphics.Bitmap;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.domain.usecases.ProfileTask;
import com.andela.taccolation.presentation.model.Course;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.model.Teacher;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

public class ProfileViewModel extends ViewModel {

    private final ProfileTask mProfileTask;
    private final MutableLiveData<Teacher> mTeacherLiveData = new MutableLiveData<>();
    private final MutableLiveData<Student> mStudentLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsDataDownloaded = new MutableLiveData<>();
    private final MutableLiveData<Map<String, List<Student>>> mStudentListPerCourse = new MutableLiveData<>();

    @ViewModelInject
    public ProfileViewModel(ProfileTask profileTask) {
        mProfileTask = profileTask;
        mIsDataDownloaded.setValue(false);
    }

    public void sendCourseList(List<Course> courses) {
        mProfileTask.sendCourseList(courses);
    }

    public void sendStudentList(List<Student> students) {
        mProfileTask.sendStudentList(students);
    }

    public LiveData<TaskStatus> addStudent(Student student, String studentPhotoPath, Bitmap bitmap) {
        byte[] imageData = null;
        if (bitmap != null)
            imageData = getByteArray(bitmap);
        return mProfileTask.addStudent(student, studentPhotoPath, imageData);
    }

    public LiveData<Teacher> getTeacher() {
        return mTeacherLiveData;
    }

    public void setTeacher(Teacher teacher) {
        mTeacherLiveData.setValue(teacher);
    }

    public LiveData<Map<String, List<Student>>> getStudentList(List<String> courseCodeList) {
        return mProfileTask.getStudentList(courseCodeList);
    }

    public LiveData<Map<String, List<Student>>> getStudentListPerCourse() {
        return mStudentListPerCourse;
    }

    public void setStudentListPerCourse(Map<String, List<Student>> studentListPerCourse) {
        mStudentListPerCourse.setValue(studentListPerCourse);
    }

    public LiveData<Student> getStudent() {
        return mStudentLiveData;
    }

    public void setStudent(Student student) {
        mStudentLiveData.setValue(student);
    }

    public LiveData<Boolean> getIsDataDownloaded() {
        return mIsDataDownloaded;
    }

    public void setIsDataDownloaded(boolean isDataDownloaded) {
        mIsDataDownloaded.setValue(isDataDownloaded);
    }

    public LiveData<TaskStatus> saveProfileImage(Bitmap bitmap, Teacher teacher) {
        return mProfileTask.saveProfileImage(getByteArray(bitmap), teacher);
    }

    private byte[] getByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
