package com.andela.taccolation.data;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.data.localdata.LocalTeacherNotesDataSource;
import com.andela.taccolation.data.remotedata.RemoteTeacherNotesDataSource;
import com.andela.taccolation.domain.repository.TeacherNotesRepo;
import com.andela.taccolation.presentation.model.Teacher;

import java.util.List;

import javax.inject.Inject;

public class TeacherNotesRepoImpl implements TeacherNotesRepo {

    private RemoteTeacherNotesDataSource mRemoteTeacherNotesDataSource;
    private LocalTeacherNotesDataSource mLocalTeacherNotesDataSource;

    @Inject
    public TeacherNotesRepoImpl(RemoteTeacherNotesDataSource remoteTeacherNotesDataSource, LocalTeacherNotesDataSource localTeacherNotesDataSource) {
        mRemoteTeacherNotesDataSource = remoteTeacherNotesDataSource;
        mLocalTeacherNotesDataSource = localTeacherNotesDataSource;
    }

    @Override
    public LiveData<TaskStatus> uploadTeacherFiles(Teacher teacher, List<String> pathList, List<String> fileNames, String courseCode) {
        return mRemoteTeacherNotesDataSource.uploadTeacherFiles(teacher, pathList, fileNames, courseCode);
    }
}
