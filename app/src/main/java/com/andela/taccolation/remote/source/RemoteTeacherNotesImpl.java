package com.andela.taccolation.remote.source;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.data.remotedata.RemoteTeacherNotesDataSource;
import com.andela.taccolation.local.entities.Notes;
import com.andela.taccolation.presentation.model.Teacher;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class RemoteTeacherNotesImpl implements RemoteTeacherNotesDataSource {

    private static final String TAG = Constants.LOG.getConstant();
    private final FirebaseFirestore mFirestore;
    private final FirebaseStorage mFirebaseStorage;
    private final MutableLiveData<TaskStatus> mUploadTeacherNotes = new MutableLiveData<>();

    @Inject
    public RemoteTeacherNotesImpl(FirebaseFirestore firestore, FirebaseStorage storage) {
        mFirestore = firestore;
        mFirebaseStorage = storage;
    }

    @Override
    public LiveData<TaskStatus> uploadTeacherFiles(Teacher teacher, List<String> pathList, List<String> fileNames, String courseCode) {
        UploadTask uploadTask;
        for (int i = 0; i < pathList.size(); i++) {
            StorageReference storageReference = mFirebaseStorage.getReference(getStorageLocation(teacher, courseCode)).child(fileNames.get(i));
            Uri uri = Uri.parse(pathList.get(i));
            Log.i(TAG, "uploadTeacherFiles: Uri #" + i + 1 + ": " + uri.toString());
            uploadTask = storageReference.putFile(uri);
            uploadTask.addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    mUploadTeacherNotes.setValue(TaskStatus.SUCCESS);
                    Log.i(TAG, "uploadTeacherFiles: Successful");
                } else {
                    mUploadTeacherNotes.setValue(TaskStatus.FAILED);
                    Log.i(TAG, "uploadTeacherFiles: Failed. Error: " + task.getException());
                    Log.i(TAG, "uploadTeacherFiles: Failed. Error: " + Objects.requireNonNull(task.getException()).getCause());
                }
            });
        }

        return mUploadTeacherNotes;
    }

    @Override
    public void insertAllNotes(Notes... teacherNotes) {

    }

    @Override
    public LiveData<List<Notes>> getAllNotes() {
        return null;
    }

    @Override
    public void deleteNote(Notes note) {

    }

    private String getStorageLocation(Teacher teacher, String courseCode) {
        return String.format("%s%s/%s_%s/", Constants.TEACHER_NOTES_PATH.getConstant(), courseCode, teacher.getFirstName(), teacher.getId());
    }
}
