package com.andela.taccolation.presentation.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.andela.taccolation.domain.usecases.ReportSheetTask;

public class ReportSheetViewModel extends ViewModel {

    private ReportSheetTask mReportSheetTask;

    @ViewModelInject
    public ReportSheetViewModel(ReportSheetTask reportSheetTask) {
        mReportSheetTask = reportSheetTask;
    }
}
