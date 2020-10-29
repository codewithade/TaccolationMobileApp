package com.andela.taccolation.presentation.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.andela.taccolation.domain.usecases.AssessmentTask;

public class AssessmentViewModel extends ViewModel {

    private AssessmentTask mAssessmentTask;

    @ViewModelInject
    public AssessmentViewModel(AssessmentTask assessmentTask) {
        mAssessmentTask = assessmentTask;
    }
}
