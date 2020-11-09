package com.andela.taccolation.data;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.data.localdata.LocalTeacherNotesDataSource;
import com.andela.taccolation.data.remotedata.RemoteTeacherNotesDataSource;
import com.andela.taccolation.domain.repository.TeacherNotesRepo;
import com.andela.taccolation.local.entities.Notes;
import com.andela.taccolation.presentation.model.Teacher;

import java.util.List;

import javax.inject.Inject;

public class TeacherNotesRepoImpl implements TeacherNotesRepo {

    private final RemoteTeacherNotesDataSource mRemoteTeacherNotesDataSource;
    private final LocalTeacherNotesDataSource mLocalTeacherNotesDataSource;

    @Inject
    public TeacherNotesRepoImpl(RemoteTeacherNotesDataSource remoteTeacherNotesDataSource, LocalTeacherNotesDataSource localTeacherNotesDataSource) {
        mRemoteTeacherNotesDataSource = remoteTeacherNotesDataSource;
        mLocalTeacherNotesDataSource = localTeacherNotesDataSource;
    }

    @Override
    public LiveData<TaskStatus> uploadTeacherFiles(Teacher teacher, List<String> pathList, List<String> fileNames, String courseCode) {
        return mRemoteTeacherNotesDataSource.uploadTeacherFiles(teacher, pathList, fileNames, courseCode);
    }

    @Override
    public void insertAllNotes(Notes... teacherNotes) {
        mLocalTeacherNotesDataSource.insertAllNotes(teacherNotes);
    }

    @Override
    public LiveData<List<Notes>> getAllNotes() {
        return mLocalTeacherNotesDataSource.getAllNotes();
    }

    @Override
    public void deleteNote(Notes note) {
        mLocalTeacherNotesDataSource.deleteNote(note);
    }
}
