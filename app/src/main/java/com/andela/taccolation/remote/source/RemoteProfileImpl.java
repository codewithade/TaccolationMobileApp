package com.andela.taccolation.remote.source;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.data.remotedata.RemoteProfileDataSource;
import com.andela.taccolation.presentation.model.Course;
import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.model.Teacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

public class RemoteProfileImpl implements RemoteProfileDataSource {
    private static final String TAG = Constants.LOG.getConstant();
    private final FirebaseFirestore mFirestore;
    private final MutableLiveData<Map<String, List<Student>>> mStudentPerCourseMap = new MutableLiveData<>();
    private final MutableLiveData<TaskStatus> mAddStudentLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Course>> mCoursesLiveData = new MutableLiveData<>();
    private final MutableLiveData<TaskStatus> mUploadTeacherProfileImage = new MutableLiveData<>();
    FirebaseStorage mFirebaseStorage;

    @Inject
    public RemoteProfileImpl(FirebaseFirestore firestore, FirebaseStorage storage) {
        mFirestore = firestore;
        mFirebaseStorage = storage;
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
                        Log.i(TAG, "onComplete: failed to send. Error: " + Objects.requireNonNull(task.getException()).getMessage());
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
                        Log.i(TAG, "onComplete: failed to send. Error: " + Objects.requireNonNull(task.getException()).getMessage());
                });
    }

    @Override
    public LiveData<TaskStatus> addStudent(Student student, String studentPhotoPath, byte[] imageData) {
        // generate AUTO-ID for new Student Document
        final String id = mFirestore.collection(Constants.STUDENTS.getConstant()).document().getId();
        student.setId(id);

        // if both image sources are null, create student with default image url
        if (studentPhotoPath == null && imageData == null) updateStudentRecord(student);
            // create Student with image from either the camera or photo gallery
        else {
            Log.i(TAG, "addStudent: STORAGE REF PATH: " + getImagePath(null, student));
            UploadTask uploadTask;
            StorageReference storageReference = mFirebaseStorage.getReference(Constants.STUDENT_IMAGE_PATH.getConstant()).child(getImagePath(null, student));
            if (imageData == null) {
                Uri file = Uri.fromFile(new File(studentPhotoPath));
                uploadTask = storageReference.putFile(file);
            } else
                uploadTask = storageReference.putBytes(imageData);

            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful())
                    throw Objects.requireNonNull(task.getException());
                return storageReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    mAddStudentLiveData.setValue(TaskStatus.FAILED);
                    Log.i(TAG, "onComplete: FAILED. Error: " + task.getException());
                } else {
                    Uri downloadUri = task.getResult();
                    if (downloadUri != null) {
                        Log.i(TAG, "onComplete: URI: " + downloadUri);
                        student.setImageUrl(downloadUri.toString());
                        updateStudentRecord(student);
                    }
                }
            });
        }

        /*mFirestore.collection(Constants.STUDENTS.getConstant()).document(id).set(student)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) mAddStudentLiveData.setValue(TaskStatus.SUCCESS);
                    else {
                        mAddStudentLiveData.setValue(TaskStatus.FAILED);
                        Log.i(TAG, "onComplete: failed. Error > " + Objects.requireNonNull(task.getException()).getMessage());
                    }
                });*/
        return mAddStudentLiveData;
    }

    @Override
    public LiveData<Map<String, List<Student>>> getStudentList(List<String> courseCodeList) {
        mFirestore.collection(Constants.STUDENTS.getConstant()).whereArrayContainsAny("courseCodeList", courseCodeList).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, List<Student>> studentListPerCourse = new HashMap<>();
                        final List<Student> studentListResult = Objects.requireNonNull(task.getResult()).toObjects(Student.class);
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
                        Log.i(TAG, "getStudentList: failed. Error: " + Objects.requireNonNull(task.getException()).getMessage());
                });
        return mStudentPerCourseMap;
    }

    @Override
    public LiveData<TaskStatus> saveProfileImage(byte[] imageData, Teacher teacher) {
        if (imageData != null) {
            Log.i(TAG, "saveProfileImage: STORAGE REF PATH: " + getImagePath(teacher, null));
            StorageReference storageReference = mFirebaseStorage.getReference(Constants.TEACHER_IMAGE_PATH.getConstant()).child(getImagePath(teacher, null));
            UploadTask uploadTask = storageReference.putBytes(imageData);
        /*uploadTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mUploadTeacherProfileImage.setValue(TaskStatus.SUCCESS);
                final Task<Uri> downloadUrl = task.getResult().getStorage().getDownloadUrl();
                Log.i(TAG, "saveProfileImage: URI: " + downloadUrl);
            } else {
                Log.i(TAG, "saveProfileImage: Failed with error: " + task.getException().getMessage());
                mUploadTeacherProfileImage.setValue(TaskStatus.FAILED);
            }
        });*/

            Task<Uri> uriTask = uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful())
                    throw Objects.requireNonNull(task.getException());
                return storageReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    mUploadTeacherProfileImage.setValue(TaskStatus.FAILED);
                    Log.i(TAG, "onComplete: FAILED. Error: " + task.getException());
                } else {
                    Uri downloadUri = task.getResult();
                    if (downloadUri != null) {
                        Log.i(TAG, "onComplete: URI: " + downloadUri);
                        teacher.setImageUrl(downloadUri.toString());
                        updateTeacherRecord(teacher);
                        mUploadTeacherProfileImage.setValue(TaskStatus.SUCCESS);
                    }
                }
            });
        } else updateTeacherRecord(teacher);
        return mUploadTeacherProfileImage;
    }

    @Override
    public LiveData<List<Course>> getCourseList() {
        mFirestore.collection(Constants.COURSES.getConstant()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.isSuccessful())
                            Log.i(TAG, "onComplete: getCourses failed. Error: " + task.getException());
                        else {
                            Log.i(TAG, "onComplete: getCourses successful.");
                            final List<Course> courses = Objects.requireNonNull(task.getResult()).toObjects(Course.class);
                            mCoursesLiveData.setValue(courses);
                        }
                    }
                });
        return mCoursesLiveData;
    }

    private void updateTeacherRecord(Teacher teacher) {
        mFirestore.collection(Constants.TEACHER.getConstant()).document(teacher.getId()).set(teacher)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mUploadTeacherProfileImage.setValue(TaskStatus.SUCCESS);
                        Log.i(TAG, "onComplete: updateTeacherRecord successful: Teacher: " + teacher.toString());
                    } else {
                        mUploadTeacherProfileImage.setValue(TaskStatus.FAILED);
                        Log.i(TAG, "onComplete: updateTeacherRecord failed. error: " + task.getException());
                    }
                });
    }

    private void updateStudentRecord(Student student) {
        mFirestore.collection(Constants.STUDENTS.getConstant()).document(student.getId()).set(student)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) mAddStudentLiveData.setValue(TaskStatus.SUCCESS);
                    else {
                        mAddStudentLiveData.setValue(TaskStatus.FAILED);
                        Log.i(TAG, "onComplete: failed. Error > " + Objects.requireNonNull(task.getException()).getMessage());
                    }
                });
    }

    private String getImagePath(Teacher teacher, Student student) {
        if (student == null)
            return String.format("%s_%s.jpg", teacher.getFirstName(), teacher.getId());
        else return String.format("%s_%s.jpg", student.getFirstName(), student.getId());
    }
}
