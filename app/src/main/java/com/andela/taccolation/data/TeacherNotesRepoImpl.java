package com.andela.taccolation.data;

import com.andela.taccolation.data.localdata.LocalTeacherNotesDataSource;
import com.andela.taccolation.data.remotedata.RemoteTeacherNotesDataSource;
import com.andela.taccolation.domain.repository.TeacherNotesRepo;

import javax.inject.Inject;

public class TeacherNotesRepoImpl implements TeacherNotesRepo {

    private RemoteTeacherNotesDataSource mRemoteTeacherNotesDataSource;
    private LocalTeacherNotesDataSource mLocalTeacherNotesDataSource;

    @Inject
    public TeacherNotesRepoImpl(RemoteTeacherNotesDataSource remoteTeacherNotesDataSource, LocalTeacherNotesDataSource localTeacherNotesDataSource) {
        mRemoteTeacherNotesDataSource = remoteTeacherNotesDataSource;
        mLocalTeacherNotesDataSource = localTeacherNotesDataSource;
    }
}
