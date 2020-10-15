package com.andela.taccolation.data;

import com.andela.taccolation.data.localdata.LocalTeacherProfileDataSource;
import com.andela.taccolation.data.remotedata.RemoteTeacherProfileDataSource;
import com.andela.taccolation.domain.repository.TeacherProfileRepo;

import javax.inject.Inject;

public class TeacherProfileRepoImpl implements TeacherProfileRepo {

    private RemoteTeacherProfileDataSource mRemoteTeacherProfileDataSource;
    private LocalTeacherProfileDataSource mLocalTeacherProfileDataSource;

    @Inject
    public TeacherProfileRepoImpl(RemoteTeacherProfileDataSource remoteTeacherProfileDataSource, LocalTeacherProfileDataSource localTeacherProfileDataSource) {
        mRemoteTeacherProfileDataSource = remoteTeacherProfileDataSource;
        mLocalTeacherProfileDataSource = localTeacherProfileDataSource;
    }
}
