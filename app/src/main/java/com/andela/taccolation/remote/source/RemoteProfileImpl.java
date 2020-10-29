package com.andela.taccolation.remote.source;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.data.remotedata.RemoteProfileDataSource;
import com.andela.taccolation.presentation.model.Course;
import com.andela.taccolation.presentation.model.Student;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class RemoteProfileImpl implements RemoteProfileDataSource {
    private static final String TAG = Constants.LOG.getConstant();
    private final FirebaseFirestore mFirestore;
    private final MutableLiveData<Map<String, List<Student>>> mStudentPerCourseMap = new MutableLiveData<>();
    private final MutableLiveData<TaskStatus> mAddStudentLiveData = new MutableLiveData<>();

    @Inject
    public RemoteProfileImpl(FirebaseFirestore firestore) {
        mFirestore = firestore;
    }

    @Override
    public void sendCourseList(List<Course> courses) {
        WriteBatch writeBatch = mFirestore.batch();
        for (Course course : courses)
            writeBatch.set(mFirestore.collection(Constants.COURSES.getConstant()).document(), course);

        writeBatch.commit()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        Log.i(TAG, "onComplete: courses sent successfully");
                    else
                        Log.i(TAG, "onComplete: failed to send. Error: " + task.getException().getMessage());
                });
    }

    @Override
    public void sendStudentList(List<Student> students) {
        WriteBatch writeBatch = mFirestore.batch();
        for (Student student : students)
            writeBatch.set(mFirestore.collection(Constants.STUDENTS.getConstant()).document(), student);
        writeBatch.commit()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        Log.i(TAG, "onComplete: students sent successfully");
                    else
                        Log.i(TAG, "onComplete: failed to send. Error: " + task.getException().getMessage());
                });
    }

    @Override
    public LiveData<TaskStatus> addStudent(Student student) {
        final String id = mFirestore.collection(Constants.STUDENTS.getConstant()).document().getId();
        student.setId(id);
        mFirestore.collection(Constants.STUDENTS.getConstant()).document(id).set(student)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) mAddStudentLiveData.setValue(TaskStatus.SUCCESS);
                    else {
                        mAddStudentLiveData.setValue(TaskStatus.FAILED);
                        Log.i(TAG, "onComplete: failed. Error > " + task.getException().getMessage());
                    }
                });
        return mAddStudentLiveData;
    }

    @Override
    public LiveData<Map<String, List<Student>>> getStudentList(List<String> courseCodeList) {
        mFirestore.collection(Constants.STUDENTS.getConstant()).whereArrayContainsAny("courseCodeList", courseCodeList).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, List<Student>> studentListPerCourse = new HashMap<>();
                        final List<Student> studentListResult = task.getResult().toObjects(Student.class);
                        Log.i(TAG, "getStudentList Result: " + studentListResult.toString());

                        for (String courseCode : courseCodeList) {
                            List<Student> studentList = new ArrayList<>();
                            for (Student student : studentListResult) {
                                if (student.getCourseCodeList().contains(courseCode))
                                    studentList.add(student);
                            }
                            studentListPerCourse.put(courseCode, studentList);
                        }
                        mStudentPerCourseMap.setValue(studentListPerCourse);
                    } else
                        Log.i(TAG, "getStudentList: failed. Error: " + task.getException().getMessage());
                });
        return mStudentPerCourseMap;
    }
}
