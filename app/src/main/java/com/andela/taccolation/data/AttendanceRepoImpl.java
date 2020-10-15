package com.andela.taccolation.data;

import com.andela.taccolation.data.localdata.LocalAttendanceDataSource;
import com.andela.taccolation.data.remotedata.RemoteAttendanceDataSource;
import com.andela.taccolation.domain.repository.AttendanceRepo;

import javax.inject.Inject;

public class AttendanceRepoImpl implements AttendanceRepo {

    private RemoteAttendanceDataSource mRemoteAttendanceDataSource;
    private LocalAttendanceDataSource mLocalAttendanceDataSource;

    @Inject
    public AttendanceRepoImpl(RemoteAttendanceDataSource remoteAttendanceDataSource, LocalAttendanceDataSource localAttendanceDataSource) {
        mRemoteAttendanceDataSource = remoteAttendanceDataSource;
        mLocalAttendanceDataSource = localAttendanceDataSource;
    }
}
