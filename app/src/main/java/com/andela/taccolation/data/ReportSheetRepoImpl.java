package com.andela.taccolation.data;

import com.andela.taccolation.data.localdata.LocalReportSheetDataSource;
import com.andela.taccolation.data.remotedata.RemoteReportSheetDataSource;
import com.andela.taccolation.domain.repository.ReportSheetRepo;

import javax.inject.Inject;

public class ReportSheetRepoImpl implements ReportSheetRepo {

    private RemoteReportSheetDataSource mRemoteReportSheetDataSource;
    private LocalReportSheetDataSource mLocalReportSheetDataSource;

    @Inject
    public ReportSheetRepoImpl(RemoteReportSheetDataSource remoteReportSheetDataSource, LocalReportSheetDataSource localReportSheetDataSource) {
        mRemoteReportSheetDataSource = remoteReportSheetDataSource;
        mLocalReportSheetDataSource = localReportSheetDataSource;
    }
}
