package com.andela.taccolation.data;

import com.andela.taccolation.data.localdata.LocalStudentProfileDataSource;
import com.andela.taccolation.data.remotedata.RemoteStudentProfileDataSource;
import com.andela.taccolation.domain.repository.StudentProfileRepo;

import javax.inject.Inject;

public class StudentProfileRepoImpl implements StudentProfileRepo {

    private RemoteStudentProfileDataSource mRemoteStudentProfileDataSource;
    private LocalStudentProfileDataSource mLocalStudentProfileDataSource;

    @Inject
    public StudentProfileRepoImpl(RemoteStudentProfileDataSource remoteStudentProfileDataSource, LocalStudentProfileDataSource localStudentProfileDataSource) {
        mRemoteStudentProfileDataSource = remoteStudentProfileDataSource;
        mLocalStudentProfileDataSource = localStudentProfileDataSource;
    }
}
