package com.andela.taccolation.domain.usecases;

import com.andela.taccolation.domain.repository.ReportSheetRepo;

import javax.inject.Inject;

public class ReportSheetTask {

    private ReportSheetRepo mReportSheetRepo;

    @Inject
    public ReportSheetTask(ReportSheetRepo reportSheetRepo) {
        mReportSheetRepo = reportSheetRepo;
    }
}
